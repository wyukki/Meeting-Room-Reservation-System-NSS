package fel.cvut.cz.room_management_system.service;

import fel.cvut.cz.room_management_system.dto.RoomDTO;
import fel.cvut.cz.room_management_system.exceptions.NotFoundException;

public interface RoomService {
    RoomDTO getRoomById(Long id) throws NotFoundException;
}
