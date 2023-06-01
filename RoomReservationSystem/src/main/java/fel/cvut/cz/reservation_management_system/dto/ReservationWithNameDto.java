package fel.cvut.cz.reservation_management_system.dto;

import java.time.ZonedDateTime;

public record ReservationWithNameDto(
        Long id, String roomName, ZonedDateTime dateFrom, ZonedDateTime dateTo) {
}
