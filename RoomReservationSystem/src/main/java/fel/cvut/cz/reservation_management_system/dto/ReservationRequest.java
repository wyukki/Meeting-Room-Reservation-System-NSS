package fel.cvut.cz.reservation_management_system.dto;

import fel.cvut.cz.reservation_management_system.enums.RecurrentEnum;

import java.time.ZonedDateTime;

public record ReservationRequest(
        Long buildingId, Long roomId, String userId, ZonedDateTime from, ZonedDateTime to, RecurrentEnum recurrent) {
}
