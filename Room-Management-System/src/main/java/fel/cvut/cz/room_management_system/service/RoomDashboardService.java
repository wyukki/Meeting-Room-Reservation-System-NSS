package fel.cvut.cz.room_management_system.service;

import fel.cvut.cz.room_management_system.dto.PagedResponse;
import fel.cvut.cz.room_management_system.dto.RoomDashboardDTO;

public interface RoomDashboardService {
    PagedResponse<RoomDashboardDTO> getRoomsForDashboard(Integer pageNo, Integer pageSize, String sortBy);
}