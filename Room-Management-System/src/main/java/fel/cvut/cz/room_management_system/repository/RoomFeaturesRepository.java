package fel.cvut.cz.room_management_system.repository;

import fel.cvut.cz.room_management_system.entity.Room;
import fel.cvut.cz.room_management_system.entity.RoomFeature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomFeaturesRepository extends JpaRepository<RoomFeature, Long> {
    void deleteAllByRoom(Room room);
}

