package fel.cvut.cz.room_management_system.repository;

import fel.cvut.cz.room_management_system.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingRepository extends JpaRepository<Building, Long> {
}
