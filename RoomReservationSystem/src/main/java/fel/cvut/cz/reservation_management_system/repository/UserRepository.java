package fel.cvut.cz.reservation_management_system.repository;

import fel.cvut.cz.reservation_management_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByEmail(String email);
}
