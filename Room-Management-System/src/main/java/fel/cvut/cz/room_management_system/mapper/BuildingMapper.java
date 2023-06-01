package fel.cvut.cz.room_management_system.mapper;

import fel.cvut.cz.room_management_system.dto.BuildingDTO;
import fel.cvut.cz.room_management_system.entity.Building;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = RoomMapper.class)
public interface BuildingMapper {
    @Mapping(source = "entity.rooms", target = "rooms")
    BuildingDTO entityToDTO(Building entity);
}
