package fel.cvut.cz.reservation_management_system.service;

import fel.cvut.cz.reservation_management_system.dto.ReservationDto;

import java.util.List;

public interface ReservationService {

    List<ReservationDto> findAllReservationsByRoomId(Long roomId);

}
