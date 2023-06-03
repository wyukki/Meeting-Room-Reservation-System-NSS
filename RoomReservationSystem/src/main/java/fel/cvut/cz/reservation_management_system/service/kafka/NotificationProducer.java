package fel.cvut.cz.reservation_management_system.service.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationProducer {

    private static final String TOPIC = "SEND_EMAIL";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendEmail(String email) {

        System.out.println("Producing notification for email -> " + email);
        this.kafkaTemplate.send(TOPIC, email);
    }
}
