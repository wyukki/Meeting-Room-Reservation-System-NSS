package fel.cvut.cz.reservation_room_rotification.kafka;

import com.mashape.unirest.http.exceptions.UnirestException;
import fel.cvut.cz.reservation_room_rotification.service.SendEmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

//    @Autowired
//    private SendEmailService sendEmailService;

    @KafkaListener(topics = "SEND_EMAIL", containerFactory="mailNotificationKafkaListenerContainerFactory")
    public void mailListener(@Payload String emailUser){
        try{
            System.out.println("Result send notification -> " + SendEmailService.sendSimpleEmail(emailUser));
        }catch(UnirestException e){
            throw new RuntimeException(e);
        }
    }
}
