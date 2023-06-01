package fel.cvut.cz.room_management_system.repository;

import fel.cvut.cz.room_management_system.entity.Room;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
