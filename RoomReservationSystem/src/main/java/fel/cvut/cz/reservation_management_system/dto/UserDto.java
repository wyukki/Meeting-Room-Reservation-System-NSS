package fel.cvut.cz.reservation_management_system.dto;

import java.util.List;

public record UserDto(Long id, String email, List<ReservationDto> reservations) {
}
