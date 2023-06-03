package fel.cvut.cz.reservation_management_system.service.impl;

import fel.cvut.cz.reservation_management_system.dto.ReservationDto;
import fel.cvut.cz.reservation_management_system.dto.ReservationRequest;
import fel.cvut.cz.reservation_management_system.dto.ReservationWithNameDto;
import fel.cvut.cz.reservation_management_system.entity.Reservation;
import fel.cvut.cz.reservation_management_system.entity.User;
import fel.cvut.cz.reservation_management_system.enums.RecurrentEnum;
import fel.cvut.cz.reservation_management_system.mapper.ReservationMapper;
import fel.cvut.cz.reservation_management_system.mapper.UserMapper;
import fel.cvut.cz.reservation_management_system.repository.ReservationRepository;
import fel.cvut.cz.reservation_management_system.repository.UserRepository;
import fel.cvut.cz.reservation_management_system.service.ReservationService;
import fel.cvut.cz.reservation_management_system.service.UserService;
import fel.cvut.cz.reservation_management_system.service.constants.RMSConstants;
import fel.cvut.cz.reservation_management_system.service.kafka.NotificationProducer;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    private final ReservationMapper reservationMapper;

    private final UserService userService;

    private final UserMapper userMapper;

    private final UserRepository userRepository;

    private final NotificationProducer notificationProducer;

    @Autowired
    public ReservationServiceImpl(
            ReservationRepository reservationRepository,
            ReservationMapper reservationMapper,
            UserService userService,
            UserMapper userMapper,
            UserRepository userRepository,
            NotificationProducer notificationProducer) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
        this.userService = userService;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.notificationProducer = notificationProducer;
    }

    @Override
    @Cacheable(cacheNames = {"reservations"})
    public List<ReservationWithNameDto> getAllReservationsByRoomId(Long id) {
        log.debug("CALL DB FROM getAllReservationsByRoomId CHECK");

        return reservationRepository.findAll().stream().filter(reservation -> reservation.getRoomId().equals(id))
                .map(reservationMapper::entityToDto).map(reservationDto ->
                        new ReservationWithNameDto(
                                reservationDto.id(),
                                getRoomName(id),
                                reservationDto.dateFrom(),
                                reservationDto.dateTo())).collect(Collectors.toList());
    }

    @Override
    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    @Override
    @Cacheable(cacheNames = {"reservations"})
    public List<ReservationDto> getReservationByUserId(Long id) {
        log.debug("CALL DB FROM getReservationByUserId CHECK");
        return reservationRepository.findAll().stream().filter(reservation -> reservation.getUser().getId().equals(id))
                .map(reservationMapper::entityToDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public boolean createReservation(ReservationRequest request) {
        if (request == null) {
            return false;
        }

        List<Reservation> allReservations = reservationRepository.findAll();

        if (request.recurrent() == null) {
            return createNonRecurrentReservation(request, allReservations);
        }

        if (RecurrentEnum.DAY.equals(request.recurrent())) {
            return createRecurrentDailyReservations(request, allReservations);
        } else if (RecurrentEnum.WEEK.equals(request.recurrent())) {
            return createRecurrentWeeklyReservations(request, allReservations);
        } else if (RecurrentEnum.MONTH.equals(request.recurrent())) {
            return createRecurrentMonthlyReservations(request, allReservations);
        }

        return false;
    }

    private boolean createNonRecurrentReservation(ReservationRequest request, List<Reservation> allReservations) {
        if (allReservations.stream()
                .anyMatch(reservation ->
                        reservationTimeIntersects(request.from(), request.to(), reservation))) {
            return false;
        }

        makeReservation(request);

        notificationProducer.sendEmail(request.userId());
        return true;
    }

    private boolean createRecurrentDailyReservations(ReservationRequest request, List<Reservation> allReservations) {
        if (IntStream.rangeClosed(0, RMSConstants.WEEK_DAYS_NUM)
                .anyMatch(i -> allReservations.stream().anyMatch(reservation ->
                        reservationTimeIntersects(request.from().plusDays(i), request.to().plusDays(i), reservation)))) {
            return false;
        }

        IntStream.rangeClosed(0, RMSConstants.WEEK_DAYS_NUM)
                .forEach(i -> makeReservation(new ReservationRequest(request.buildingId(), request.roomId(),
                        request.userId(), request.from().plusDays(i), request.to().plusDays(i), request.recurrent())));

        notificationProducer.sendEmail(request.userId());

        return true;
    }

    private boolean createRecurrentWeeklyReservations(ReservationRequest request, List<Reservation> allReservations) {
        if (IntStream.rangeClosed(0, RMSConstants.WEEKS_IN_MONTH_NUM)
                .anyMatch(i -> allReservations.stream().anyMatch(reservation ->
                        reservationTimeIntersects(request.from().plusWeeks(i), request.to().plusWeeks(i), reservation)))) {
            return false;
        }

        IntStream.rangeClosed(0, RMSConstants.WEEKS_IN_MONTH_NUM)
                .forEach(i -> makeReservation(new ReservationRequest(request.buildingId(), request.roomId(), request.userId(),
                        request.from().plusWeeks(i), request.to().plusWeeks(i), request.recurrent())));

        notificationProducer.sendEmail(request.userId());

        return true;
    }

    private boolean createRecurrentMonthlyReservations(ReservationRequest request, List<Reservation> allReservations) {
        if (IntStream.rangeClosed(0, RMSConstants.SIX_MONTHS)
                .anyMatch(i -> allReservations.stream().anyMatch(reservation ->
                        reservationTimeIntersects(request.from().plusMonths(i), request.to().plusMonths(i), reservation)))) {
            return false;
        }

        IntStream.rangeClosed(0, RMSConstants.SIX_MONTHS)
                .forEach(i -> makeReservation(new ReservationRequest(request.buildingId(), request.roomId(), request.userId(),
                        request.from().plusMonths(i), request.to().plusMonths(i), request.recurrent())));

        notificationProducer.sendEmail(request.userId());

        return true;
    }


    private void makeReservation(ReservationRequest request) {
        Reservation reservation = new Reservation();
        reservation.setRoomId(request.roomId());
        reservation.setDateFrom(request.from());
        reservation.setDateTo(request.to());

        User user = userMapper.dtoToEntity(userService.getOrCreateUserByEmail(request.userId()));
        user.addReservation(reservation);

        reservation.setUser(user);

        userRepository.save(user);
        reservationRepository.save(reservation);
    }

    private boolean reservationTimeIntersects(ZonedDateTime from, ZonedDateTime to, Reservation reservation) {
        if (to.isBefore(reservation.getDateFrom()) || to.isEqual(reservation.getDateFrom())
                || from.isAfter(reservation.getDateTo()) || from.isEqual(reservation.getDateTo())) {
            return false;
        }
        return true;
    }

    private String getRoomName(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(RMSConstants.ROOM_MANAGEMENT_SYSTEM_URL + "/" + id.toString(), String.class).getBody();
    }
}
