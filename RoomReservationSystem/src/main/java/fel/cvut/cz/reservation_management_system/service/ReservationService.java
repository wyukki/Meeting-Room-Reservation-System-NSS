package fel.cvut.cz.reservation_management_system.service;

import fel.cvut.cz.reservation_management_system.dto.ReservationDto;
import fel.cvut.cz.reservation_management_system.dto.ReservationRequest;
import fel.cvut.cz.reservation_management_system.dto.ReservationWithNameDto;

import java.util.List;

public interface ReservationService {

    /**
     * @param id - room identifier
     * @return - list of all reservations
     */
    List<ReservationWithNameDto> getAllReservationsByRoomId(Long id);

    /**
     * @param id - reservation identifier
     */
    void deleteReservation(Long id);


    /**
     * @param id - user identifier
     * @return list of all user reservations
     */
    List<ReservationDto> getReservationByUserId(Long id);


    /**
     * Creates a reservation for a specific room.
     * It is possible to create either recurrent or non-recurrent reservations.
     * 3 kinds of recurrent reservations are available:
     * DAY - daily reservation (reservation will be created for next week),
     * WEEK - weekly reservation (reservation will be created for next 4 weeks),
     * MONTH - monthly reservation (reservation will be created for next 4 weeks).
     * <p>
     * First checks if it's possible to create reservation for required time,
     * if yes, creates reservation.
     *
     * @param request includes necessary information as:
     *                room identification,
     *                user identification,
     *                time interval
     * @return true if reservation was successfully created, false otherwise
     */
    boolean createReservation(ReservationRequest request);
}
