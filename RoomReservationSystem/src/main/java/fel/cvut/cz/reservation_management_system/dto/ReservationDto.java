package fel.cvut.cz.reservation_management_system.dto;

import java.time.ZonedDateTime;

public record ReservationDto(Long id, Long roomId, ZonedDateTime dateFrom, ZonedDateTime dateTo) {

}
