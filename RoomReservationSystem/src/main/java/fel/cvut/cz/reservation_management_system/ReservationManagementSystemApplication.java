package fel.cvut.cz.reservation_management_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ReservationManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReservationManagementSystemApplication.class, args);
    }

}
