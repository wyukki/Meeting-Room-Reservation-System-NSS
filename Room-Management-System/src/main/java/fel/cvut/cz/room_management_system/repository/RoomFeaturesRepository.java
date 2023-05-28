package fel.cvut.cz.room_management_system.repository;

import fel.cvut.cz.room_management_system.entity.RoomFeature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomFeaturesRepository extends JpaRepository<RoomFeature, Long> {
}
