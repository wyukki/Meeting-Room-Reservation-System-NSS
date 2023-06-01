package fel.cvut.cz.reservation_management_system.service;

import fel.cvut.cz.reservation_management_system.dto.ReservationDto;
import fel.cvut.cz.reservation_management_system.dto.ReservationWithNameDto;

import java.util.List;

public interface ReservationService {

    List<ReservationWithNameDto> getAllReservationsByRoomId(Long id);

    void deleteReservation(Long id);

    List<ReservationDto> getReservationByUserId(Long id);

}
