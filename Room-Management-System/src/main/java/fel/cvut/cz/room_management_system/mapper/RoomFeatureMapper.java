package fel.cvut.cz.room_management_system.mapper;

import fel.cvut.cz.room_management_system.dto.RoomFeatureDTO;
import fel.cvut.cz.room_management_system.entity.RoomFeature;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoomFeatureMapper {
    RoomFeatureDTO entityToDTO(RoomFeature roomFeature);
}
