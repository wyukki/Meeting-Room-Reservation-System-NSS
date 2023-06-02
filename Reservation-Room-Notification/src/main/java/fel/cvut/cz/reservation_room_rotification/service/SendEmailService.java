package fel.cvut.cz.reservation_room_rotification.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SendEmailService {

    private static final String DOMAIN_NAME = "sandbox4d26efa5916c46df9994bf47d11ddf6a.mailgun.org";

    /**
     * The method gets the recipient's email, configures the email, and sends the email
     * @param email Who we're sending the letter to
     * @return the result if the email is sent or returns an exception
     */
    public static JsonNode sendSimpleEmail(String emailUser) throws UnirestException {
        HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/" + DOMAIN_NAME + "/messages")
                .basicAuth("api", MailgunKey.API_KEY)
                .queryString("from", "remelNotification@test.com")
                .queryString("to", emailUser)
                .queryString("subject", "New room reservation")
                .queryString("text", "Your room reservation is successful")
                .asJson();
        log.info("Mailgun send email about reservation to email ->" + emailUser);
        return request.getBody();
    }
}
