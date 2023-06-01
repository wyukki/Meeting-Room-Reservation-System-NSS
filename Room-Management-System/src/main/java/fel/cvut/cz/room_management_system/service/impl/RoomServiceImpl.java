package fel.cvut.cz.room_management_system.service.impl;

import fel.cvut.cz.room_management_system.dto.FeatureDTO;
import fel.cvut.cz.room_management_system.dto.RoomDTO;
import fel.cvut.cz.room_management_system.dto.RoomFeatureDTO;
import fel.cvut.cz.room_management_system.entity.Feature;
import fel.cvut.cz.room_management_system.entity.Room;
import fel.cvut.cz.room_management_system.entity.RoomFeature;
import fel.cvut.cz.room_management_system.enums.RoomFeatureEnum;
import fel.cvut.cz.room_management_system.exceptions.NotFoundException;
import fel.cvut.cz.room_management_system.mapper.RoomFeatureMapper;
import fel.cvut.cz.room_management_system.mapper.RoomMapper;
import fel.cvut.cz.room_management_system.repository.FeatureRepository;
import fel.cvut.cz.room_management_system.repository.RoomFeaturesRepository;
import fel.cvut.cz.room_management_system.repository.RoomRepository;
import fel.cvut.cz.room_management_system.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final RoomFeaturesRepository roomFeaturesRepository;
    private final FeatureRepository featureRepository;
    private final RoomMapper roomMapper;
    private final RoomFeatureMapper roomFeatureMapper;

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository, RoomFeaturesRepository roomFeaturesRepository, FeatureRepository featureRepository, RoomMapper roomMapper, RoomFeatureMapper roomFeatureMapper) {
        this.roomRepository = roomRepository;
        this.roomFeaturesRepository = roomFeaturesRepository;
        this.featureRepository = featureRepository;
        this.roomMapper = roomMapper;
        this.roomFeatureMapper = roomFeatureMapper;
    }

    @Override
    public RoomDTO getRoomById(Long id) throws NotFoundException {
        roomRepository.findById(id);
        return roomMapper.entityToDTO(roomRepository.findById(id).orElseThrow(() -> new NotFoundException("Room with id " + id + " does not exist")));
    }

    @Override
    public List<RoomFeatureDTO> updateRoomFeatures(Long id, List<FeatureDTO> requestFeatures) throws NotFoundException {
        Room room = roomRepository.findById(id).orElseThrow(() -> new NotFoundException("Room with id " + id + " does not exist"));
        List<Feature> existingFeatures = room.getRoomFeatures().stream().map(RoomFeature::getFeature).toList();
        List<FeatureDTO> toSave = getListOfFeaturesToSave(requestFeatures, existingFeatures);
        List<Feature> toDelete = getListOfFeaturesToDelete(requestFeatures, existingFeatures);
        featureRepository.deleteAll(toDelete);

        for (FeatureDTO dto : toSave) {
            Feature featureToSave = featureRepository.
                    findByFeatureName(RoomFeatureEnum.fromValue(dto.name())).
                    orElseThrow(() -> new NotFoundException("Feature with name " + dto.name() + " doesn't exists"));
            RoomFeature roomFeature = new RoomFeature();
            roomFeature.setRoom(room);
            roomFeature.setFeature(featureToSave);
            roomFeaturesRepository.save(roomFeature);
        }
        return room.getRoomFeatures().stream().map(roomFeatureMapper::entityToDTO).collect(Collectors.toList());
    }

    private List<FeatureDTO> getListOfFeaturesToSave(List<FeatureDTO> requestFeatures, List<Feature> existingFeatures) {
        List<FeatureDTO> toSave = new ArrayList<>();
        for (FeatureDTO dto : requestFeatures) {
            if (!isInExistingFeatureList(dto, existingFeatures)) {
                toSave.add(dto);
            }
        }
        return toSave;
    }

    private List<Feature> getListOfFeaturesToDelete(List<FeatureDTO> requestFeatures, List<Feature> existingFeatures) {
        List<Feature> toDelete = new ArrayList<>();
        for (Feature feature : existingFeatures) {
            if (!isInRequestFeatureList(feature, requestFeatures)) {
                toDelete.add(feature);
            }
        }
        return toDelete;
    }

    private boolean isInExistingFeatureList(FeatureDTO dto, List<Feature> existing) {
        for (Feature feature : existing) {
            if (feature.getFeatureName().getValue().equals(dto.name())) {
                return true;
            }
        }
        return false;
    }

    private boolean isInRequestFeatureList(Feature feature, List<FeatureDTO> dtos) {
        for (FeatureDTO dto : dtos) {
            if (dto.name() != null && !dto.name().isEmpty()
                    && dto.name().equals(feature.getFeatureName().getValue())) {
                return true;
            }
        }
        return false;
    }
}
