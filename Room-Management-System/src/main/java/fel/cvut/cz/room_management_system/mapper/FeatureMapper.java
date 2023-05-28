package fel.cvut.cz.room_management_system.mapper;

import fel.cvut.cz.room_management_system.dto.FeatureDTO;
import fel.cvut.cz.room_management_system.entity.Feature;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring"
)
public interface FeatureMapper {
    @Mapping(target = "name", expression = "java(feature.getFeatureName().name())")
    FeatureDTO entityToDTO(Feature feature);
}
