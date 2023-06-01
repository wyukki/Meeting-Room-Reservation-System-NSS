package fel.cvut.cz.reservation_management_system.repository;

import fel.cvut.cz.reservation_management_system.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
