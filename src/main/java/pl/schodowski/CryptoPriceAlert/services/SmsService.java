package pl.schodowski.CryptoPriceAlert.services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.schodowski.CryptoPriceAlert.exceptions.CryptoPriceAlertException;


@Service
public class SmsService {

    private final String ACCOUNT_SID;
    private final String AUTH_TOKEN;
    private final String TWILIO_PHONE_NUMBER;
    private final String RECEIVER_PHONE_NUMBER;

    public SmsService(@Value("${twilio.account_sid}") String ACCOUNT_SID,
                      @Value("${twilio.auth_token}") String AUTH_TOKEN,
                      @Value("${twilio.phone_number}") String TWILIO_PHONE_NUMBER,
                      @Value("${twilio.receiver_phone_number}") String RECEIVER_PHONE_NUMBER) {
        this.ACCOUNT_SID = ACCOUNT_SID;
        this.AUTH_TOKEN = AUTH_TOKEN;
        this.TWILIO_PHONE_NUMBER = TWILIO_PHONE_NUMBER;
        this.RECEIVER_PHONE_NUMBER = RECEIVER_PHONE_NUMBER;
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
