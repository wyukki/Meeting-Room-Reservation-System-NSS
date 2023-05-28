package fel.cvut.cz.room_management_system.service;

import fel.cvut.cz.room_management_system.dto.RoomDashboardDTO;

import java.util.List;

public interface RoomDashboardService {
    List<RoomDashboardDTO> getDashboardRooms();
}
