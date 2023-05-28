package fel.cvut.cz.room_management_system.service.impl;

import fel.cvut.cz.room_management_system.dto.BuildingDTO;
import fel.cvut.cz.room_management_system.exceptions.NotFoundException;
import fel.cvut.cz.room_management_system.mapper.BuildingMapper;
import fel.cvut.cz.room_management_system.repository.BuildingRepository;
import fel.cvut.cz.room_management_system.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BuildingServiceImpl implements BuildingService {
    private final BuildingRepository buildingRepository;
    private final BuildingMapper mapper;

    @Autowired
    public BuildingServiceImpl(BuildingRepository buildingRepository, BuildingMapper mapper) {
        this.buildingRepository = buildingRepository;
        this.mapper = mapper;
    }

    @Override
    public BuildingDTO getAllRoomsByBuildingId(Long id) throws NotFoundException {
        return mapper.entityToDTO(buildingRepository.findById(id).orElseThrow(() -> new NotFoundException("Building with id " + id + " does not exist")));
    }

    @Override
    public List<BuildingDTO> getAllBuildings() {
        return buildingRepository.findAll().stream().map(mapper::entityToDTO).collect(Collectors.toList());
    }
}
