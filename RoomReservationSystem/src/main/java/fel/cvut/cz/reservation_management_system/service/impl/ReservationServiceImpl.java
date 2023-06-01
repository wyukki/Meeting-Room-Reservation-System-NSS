package fel.cvut.cz.reservation_management_system.service.impl;

import fel.cvut.cz.reservation_management_system.dto.ReservationDto;
import fel.cvut.cz.reservation_management_system.dto.ReservationWithNameDto;
import fel.cvut.cz.reservation_management_system.mapper.ReservationMapper;
import fel.cvut.cz.reservation_management_system.repository.ReservationRepository;
import fel.cvut.cz.reservation_management_system.service.ReservationService;
import fel.cvut.cz.reservation_management_system.service.constants.RMSCons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    private final ReservationMapper reservationMapper;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository, ReservationMapper reservationMapper) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
    }

    @Override
    public List<ReservationWithNameDto> getAllReservationsByRoomId(Long id) {
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
    public List<ReservationDto> getReservationByUserId(Long id) {
        return reservationRepository.findAll().stream().filter(reservation -> reservation.getUser().getId().equals(id))
                .map(reservationMapper::entityToDto).collect(Collectors.toList());
    }

    private String getRoomName(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(RMSCons.ROOM_MANAGEMENT_SYSTEM_URL + "/" + id.toString(), String.class).getBody();
    }
}
