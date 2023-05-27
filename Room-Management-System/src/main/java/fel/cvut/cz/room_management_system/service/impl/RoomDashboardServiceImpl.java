package fel.cvut.cz.room_management_system.service.impl;

import fel.cvut.cz.room_management_system.dto.RoomDashboardDTO;
import fel.cvut.cz.room_management_system.mapper.RoomDashboardMapper;
import fel.cvut.cz.room_management_system.repository.RoomRepository;
import fel.cvut.cz.room_management_system.service.RoomDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomDashboardServiceImpl implements RoomDashboardService {
    private final RoomRepository repository;
    private final RoomDashboardMapper mapper;

    @Autowired
    public RoomDashboardServiceImpl(RoomRepository repository, RoomDashboardMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<RoomDashboardDTO> getDashboardRooms() {
        return repository.findAll().stream().map(mapper::roomToDashboardDTO).collect(Collectors.toList());
    }
}
