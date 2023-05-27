package fel.cvut.cz.room_management_system.service.impl;

import fel.cvut.cz.room_management_system.dto.RoomDTO;
import fel.cvut.cz.room_management_system.entity.Room;
import fel.cvut.cz.room_management_system.exceptions.NotFoundException;
import fel.cvut.cz.room_management_system.mapper.RoomMapper;
import fel.cvut.cz.room_management_system.repository.RoomRepository;
import fel.cvut.cz.room_management_system.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomServiceImpl implements RoomService {
    private final RoomRepository repository;
    private final RoomMapper mapper;

    @Autowired
    public RoomServiceImpl(RoomRepository repository, RoomMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public RoomDTO getRoomById(Long id) throws NotFoundException {
        repository.findById(id);
        return mapper.entityToDTO(repository.findById(id).orElseThrow(() -> new NotFoundException("Room with id " + id + " does not exist")));
    }
}
