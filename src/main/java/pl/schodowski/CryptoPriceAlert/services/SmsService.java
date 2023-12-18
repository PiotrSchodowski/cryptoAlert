package pl.schodowski.CryptoPriceAlert.services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.schodowski.CryptoPriceAlert.exceptions.CryptoPriceAlertException;


@Service
public class SmsService {

    private final String ACCOUNT_SID = "ACf690f10d89ab0ca7d80da14e696f153c";
    private final String AUTH_TOKEN = "b43ef950564e1f3b25bc3f4bae6c008a";
    private final String TWILIO_PHONE_NUMBER = "+12058585764";
    private final String RECEIVER_PHONE_NUMBER = "+48798226220";

    public SmsService() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public ResponseEntity<String> sendSMS(String message) {
        try {
            Message.creator(
                            new com.twilio.type.PhoneNumber(RECEIVER_PHONE_NUMBER),
                            new com.twilio.type.PhoneNumber(TWILIO_PHONE_NUMBER),
                            message)
                    .create();
            System.out.println("SMS sent successfully: " + message);
            return ResponseEntity.ok("SMS sent successfully: " + message);
        } catch (Exception e) {
            throw new CryptoPriceAlertException("SMS sending failed: " + e.getMessage());
        }
    }
}
