package fel.cvut.cz.room_management_system.service;

import fel.cvut.cz.room_management_system.dto.FeatureDTO;
import fel.cvut.cz.room_management_system.dto.RoomDTO;
import fel.cvut.cz.room_management_system.dto.RoomFeatureDTO;
import fel.cvut.cz.room_management_system.exceptions.NotFoundException;

import java.util.List;

public interface RoomService {
    RoomDTO getRoomById(Long id) throws NotFoundException;

    List<RoomFeatureDTO> updateRoomFeatures(Long id, List<FeatureDTO> requestFeatures) throws NotFoundException;
}
