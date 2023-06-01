package fel.cvut.cz.room_management_system.service;

import fel.cvut.cz.room_management_system.dto.BuildingDTO;
import fel.cvut.cz.room_management_system.exceptions.NotFoundException;

import java.util.List;

public interface BuildingService {
    BuildingDTO getAllRoomsByBuildingId(Long id) throws NotFoundException;

    List<BuildingDTO> getAllBuildings();
}
