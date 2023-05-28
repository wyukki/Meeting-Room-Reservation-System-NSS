package fel.cvut.cz.room_management_system.dto;

public record RoomDashboardDTO(Long id, Long building_id, String name, String description, String imgURL,
                               Boolean occupiedNow) {
}
