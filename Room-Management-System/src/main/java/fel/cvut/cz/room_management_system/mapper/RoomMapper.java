package fel.cvut.cz.room_management_system.mapper;

import fel.cvut.cz.room_management_system.dto.RoomDTO;
import fel.cvut.cz.room_management_system.entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = RoomFeatureMapper.class)
public interface RoomMapper {
    @Mapping(target = "buildingId", expression = "java(room.getBuilding().getId())")
    @Mapping(target = "roomLayout", expression = "java(room.getRoomLayout().name())")
    @Mapping(target = "noiseLevel", expression = "java(room.getNoiseLevel().name())")
    RoomDTO entityToDTO(Room room);

    @Mapping(source = "dto.id", target = "id", ignore = true)
    Room dtoToEntity(RoomDTO dto);
}
