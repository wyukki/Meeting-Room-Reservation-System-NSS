package fel.cvut.cz.room_management_system.mapper;

import fel.cvut.cz.room_management_system.dto.RoomDashboardDTO;
import fel.cvut.cz.room_management_system.entity.Room;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoomDashboardMapper {
    RoomDashboardDTO roomToDashboardDTO(Room room);

    Room dashboardDTOtoRoom(RoomDashboardDTO dto);
}
