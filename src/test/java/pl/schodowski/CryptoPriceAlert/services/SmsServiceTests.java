package pl.schodowski.CryptoPriceAlert.services;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import pl.schodowski.CryptoPriceAlert.services.SmsService;

import static org.assertj.core.api.Assertions.assertThat;


public class SmsServiceTests {

    @InjectMocks
    private SmsService smsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSendSMS() {

        String message = "Test message";
        ResponseEntity<String> response = smsService.sendSMS(message);

        assertThat(response.getBody()).isEqualTo("SMS sent successfully: " + message);
    }


}
