package pl.schodowski.CryptoPriceAlert.services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.stereotype.Service;


@Service
public class SmsService {


    private final String ACCOUNT_SID = "ACf690f10d89ab0ca7d80da14e696f153c";
    private final String AUTH_TOKEN = "dbac523be6bb13c574878a41d7485389";
    private final String TWILIO_PHONE_NUMBER = "+14153602693";
    private final String RECEIVER_PHONE_NUMBER = "+48720745816";

    public SmsService() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public void sendSMS(String message) {
        try {
            Message.creator(
                            new com.twilio.type.PhoneNumber(RECEIVER_PHONE_NUMBER),
                            new com.twilio.type.PhoneNumber(TWILIO_PHONE_NUMBER),
                            message)
                    .create();
            System.out.println("SMS sent successfully: " + message);
        } catch (Exception e) {
            System.err.println("Error sending SMS: " + e.getMessage());
        }
    }

}
