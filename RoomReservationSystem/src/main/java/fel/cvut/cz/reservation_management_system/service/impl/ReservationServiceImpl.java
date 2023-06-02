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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    private final ReservationMapper reservationMapper;

    private final UserService userService;

    private final UserMapper userMapper;

    private final UserRepository userRepository;

    @Autowired
    public ReservationServiceImpl(
            ReservationRepository reservationRepository,
            ReservationMapper reservationMapper,
            UserService userService,
            UserMapper userMapper,
            UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
        this.userService = userService;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    @Override
    @Cacheable(cacheNames = {"reservations"})
    public List<ReservationWithNameDto> getAllReservationsByRoomId(Long id) {
        System.out.println("CALL DB FROM getAllReservationsByRoomId");

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
        System.out.println("CALL DB FROM getReservationByUserId");
        return reservationRepository.findAll().stream().filter(reservation -> reservation.getUser().getId().equals(id))
                .map(reservationMapper::entityToDto).collect(Collectors.toList());
    }

    @Override
    public boolean createReservation(ReservationRequest request) {
        if (request == null) {
            return false;
        }

        if (request.recurrent() == null) {
            //create one time reservation
            for (Reservation reservation : reservationRepository.findAll()) {
                if (reservationTimeIntersects(request.from(), request.to(), reservation)) {
                    return false;
                }
            }

            makeReservation(request);

            return true;
        }

        //create recurrent reservations
        //if we have daily frequency, reservation will be created for next week
        //if we have weekly frequency, reservation will be created for next 4 weeks
        //if we have monthly frequency, reservation will be created for next 6 month
        if (RecurrentEnum.DAY.equals(request.recurrent())) {
            for (int i = 0; i <= 7; ++i) {
                for (Reservation reservation : reservationRepository.findAll()) {
                    if (reservationTimeIntersects(request.from().plusDays(i), request.to().plusDays(i), reservation)) {
                        return false;
                    }
                }
            }

            for (int i = 0; i <= 7; ++i) {
                makeReservation(new ReservationRequest(request.buildingId(), request.roomId(), request.userId(),
                        request.from().plusDays(i), request.to().plusDays(i), request.recurrent()));
            }

            return true;
        } else if (RecurrentEnum.WEEK.equals(request.recurrent())) {
            //check every week
            for (int i = 0; i <= 4; ++i) {
                for (Reservation reservation : reservationRepository.findAll()) {
                    if (reservationTimeIntersects(request.from().plusWeeks(i), request.to().plusWeeks(i), reservation)) {
                        return false;
                    }
                }
            }

            for (int i = 0; i <= 4; ++i) {
                makeReservation(new ReservationRequest(request.buildingId(), request.roomId(), request.userId(),
                        request.from().plusWeeks(i), request.to().plusWeeks(i), request.recurrent()));
            }

            return true;
        } else if (RecurrentEnum.MONTH.equals(request.recurrent())) {
            for (int i = 0; i <= 6; ++i) {
                for (Reservation reservation : reservationRepository.findAll()) {
                    if (reservationTimeIntersects(request.from().plusMonths(i), request.to().plusMonths(i), reservation)) {
                        return false;
                    }
                }
            }

            for (int i = 0; i <= 6; ++i) {
                makeReservation(new ReservationRequest(request.buildingId(), request.roomId(), request.userId(),
                        request.from().plusMonths(i), request.to().plusMonths(i), request.recurrent()));
            }

            return true;
        } else if (RecurrentEnum.YEAR.equals(request.recurrent())) {
            //TODO
            System.out.println("Not implemented yet!");
            return false;
        } else {
            //unsupported RecurrentPeriod
            return false;
        }
    }

    private void makeReservation(ReservationRequest request) {
        Reservation reservation = new Reservation();
        reservation.setRoomId(request.roomId());
        reservation.setDateFrom(request.from());
        reservation.setDateTo(request.to());

        //getOrCreate user
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
