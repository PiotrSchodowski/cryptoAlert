package pl.schodowski.CryptoPriceAlert.unitTests;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
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
        assertThat(smsService.sendSMS(message)).isTrue();
    }


}
