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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    @Transactional
    public List<RoomFeatureDTO> updateRoomFeatures(Long id, List<FeatureDTO> requestFeatures) throws NotFoundException {
//        Room room = roomRepository.findById(id)
//                .orElseThrow(() -> new NotFoundException("Room with id " + id + " does not exist"));
//
//        roomFeaturesRepository.deleteAllByRoom(room);
//
//        for (FeatureDTO dto : requestFeatures) {
//            Feature featureToSave = featureRepository.findByFeatureName(RoomFeatureEnum.fromValue(dto.name()))
//                    .orElseThrow(() -> new NotFoundException("Feature with name " + dto.name() + " doesn't exist"));
//
//            RoomFeature roomFeature = new RoomFeature();
//            roomFeature.setRoom(room);
//            roomFeature.setFeature(featureToSave);
//
//            // Add roomFeature to room's collection
//            room.getRoomFeatures().add(roomFeature);
//
//            // Add roomFeature to feature's collection
//            featureToSave.getRoomFeatures().add(roomFeature);
//            featureRepository.save(featureToSave);
//            roomRepository.save(room);
//            roomFeaturesRepository.save(roomFeature);
//        }
//        return room.getRoomFeatures().stream().map(roomFeatureMapper::entityToDTO).collect(Collectors.toList());
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Room with id " + id + " does not exist"));

        roomFeaturesRepository.deleteAllByRoom(room);

        room.getRoomFeatures().clear();

        Set<RoomFeature> saved = new HashSet<>();

        for (FeatureDTO dto : requestFeatures) {
            Feature featureToSave = featureRepository.findByFeatureName(RoomFeatureEnum.fromValue(dto.name()))
                    .orElseThrow(() -> new NotFoundException("Feature with name " + dto.name() + " doesn't exist"));

            RoomFeature roomFeature = new RoomFeature();
            roomFeature.setRoom(room);
            roomFeature.setFeature(featureToSave);

            // Add roomFeature to room's collection
            room.getRoomFeatures().add(roomFeature);

            // Add roomFeature to feature's collection
            featureToSave.getRoomFeatures().add(roomFeature);

            saved.add(roomFeature);
        }

        room.setRoomFeatures(saved);
        roomRepository.save(room);

        return room.getRoomFeatures().stream().map(roomFeatureMapper::entityToDTO).collect(Collectors.toList());
    }
}
