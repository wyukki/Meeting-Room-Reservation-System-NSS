package fel.cvut.cz.room_management_system.dto;

import java.util.List;

public record BuildingDTO(Long id, String name, List<RoomDTO> rooms) {
}
