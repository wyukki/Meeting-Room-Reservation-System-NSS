package fel.cvut.cz.room_management_system.repository;

import fel.cvut.cz.room_management_system.entity.Feature;
import fel.cvut.cz.room_management_system.enums.RoomFeatureEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeatureRepository extends JpaRepository<Feature, Long> {
    Optional<Feature> findByFeatureName(RoomFeatureEnum value);
}
