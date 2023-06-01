package fel.cvut.cz.reservation_management_system.service.impl;

import fel.cvut.cz.reservation_management_system.dto.ReservationDto;
import fel.cvut.cz.reservation_management_system.mapper.ReservationMapper;
import fel.cvut.cz.reservation_management_system.repository.ReservationRepository;
import fel.cvut.cz.reservation_management_system.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<ReservationDto> findAllReservationsByRoomId(Long id) {
        return reservationRepository.findAll().stream().filter(reservation -> reservation.getRoomId().equals(id))
                .map(reservationMapper::entityToDto).collect(Collectors.toList());
    }
}
