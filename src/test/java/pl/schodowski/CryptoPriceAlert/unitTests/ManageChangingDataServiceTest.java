package pl.schodowski.CryptoPriceAlert.unitTests;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import pl.schodowski.CryptoPriceAlert.repo.Crypto;
import pl.schodowski.CryptoPriceAlert.services.CalculateService;
import pl.schodowski.CryptoPriceAlert.services.ManageChangingDataService;
import pl.schodowski.CryptoPriceAlert.services.SmsService;

import static org.mockito.Mockito.when;

public class ManageChangingDataServiceTest {

    @InjectMocks
    private ManageChangingDataService manageChangingDataService;

    @Mock
    private CalculateService calculateService;

    @Mock
    private SmsService smsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void shouldReturnValidMessageWhenPriceMoreIncreaseChange(){

        Crypto cryptoAfterScrapping = new Crypto();
        cryptoAfterScrapping.setName("Bitcoin");
        cryptoAfterScrapping.setPrice(1000f);

        Crypto cryptoFromDatabase = new Crypto();
        cryptoFromDatabase.setName("Bitcoin");
        cryptoFromDatabase.setPrice(1100f);

        when(calculateService.calculatePercentageChange(cryptoFromDatabase.getPrice(), cryptoAfterScrapping.getPrice())).thenReturn(10f);
        when(smsService.sendSMS("$$$ CRYPTO-ALERT $$$ Bitcoin price is rising: 10.0% in just 10 minutes!")).thenReturn(ResponseEntity.ok("$$$ CRYPTO-ALERT $$$ Bitcoin price is rising: 10.0% in just 5 minutes!"));

        ResponseEntity<String> response = manageChangingDataService.manageIncreasePrice(cryptoAfterScrapping, cryptoFromDatabase);
        Assertions.assertThat(response.getBody()).isEqualTo("$$$ CRYPTO-ALERT $$$ Bitcoin price is rising: 10.0% in just 5 minutes!");
    }

    @Test
    void shouldReturnValidMessageWhenPriceMoreDecreaseChange(){

        Crypto cryptoAfterScrapping = new Crypto();
        cryptoAfterScrapping.setName("Bitcoin");
        cryptoAfterScrapping.setPrice(1000f);

        Crypto cryptoFromDatabase = new Crypto();
        cryptoFromDatabase.setName("Bitcoin");
        cryptoFromDatabase.setPrice(900f);

        when(calculateService.calculatePercentageChange(cryptoFromDatabase.getPrice(), cryptoAfterScrapping.getPrice())).thenReturn(-10f);
        when(smsService.sendSMS("$$$ CRYPTO-ALERT $$$ Bitcoin price is falling: -10.0% in just 10 minutes!")).thenReturn(ResponseEntity.ok("$$$ CRYPTO-ALERT $$$ Bitcoin price is falling: -10.0% in just 5 minutes!"));

        ResponseEntity<String> response = manageChangingDataService.manageDecreasePrice(cryptoAfterScrapping, cryptoFromDatabase);
        Assertions.assertThat(response.getBody()).isEqualTo("$$$ CRYPTO-ALERT $$$ Bitcoin price is falling: -10.0% in just 10 minutes!");
    }

    @Test
    void shouldReturnValidMessageWhenVolumeMoreDecreaseChange(){

        Crypto cryptoAfterScrapping = new Crypto();
        cryptoAfterScrapping.setName("Bitcoin");
        cryptoAfterScrapping.setTotalVolume(1000f);

        Crypto cryptoFromDatabase = new Crypto();
        cryptoFromDatabase.setName("Bitcoin");
        cryptoFromDatabase.setTotalVolume(900f);

        when(calculateService.calculatePercentageChange(cryptoFromDatabase.getTotalVolume(), cryptoAfterScrapping.getTotalVolume())).thenReturn(-10f);
        when(smsService.sendSMS("$$$ CRYPTO-ALERT $$$ Bitcoin volume is falling: -10.0% in just 10 minutes!")).thenReturn(ResponseEntity.ok("$$$ CRYPTO-ALERT $$$ Bitcoin volume is falling: -10.0% in just 5 minutes!"));

        ResponseEntity<String> response = manageChangingDataService.manageDecreaseVolume(cryptoAfterScrapping, cryptoFromDatabase);
        Assertions.assertThat(response.getBody()).isEqualTo("$$$ CRYPTO-ALERT $$$ Bitcoin volume is falling: -10.0% in just 10 minutes!");
    }

    @Test
    void shouldReturnValidMessageWhenVolumeMoreIncreaseChange(){

        Crypto cryptoAfterScrapping = new Crypto();
        cryptoAfterScrapping.setName("Bitcoin");
        cryptoAfterScrapping.setTotalVolume(1000f);

        Crypto cryptoFromDatabase = new Crypto();
        cryptoFromDatabase.setName("Bitcoin");
        cryptoFromDatabase.setTotalVolume(1100f);

        when(calculateService.calculatePercentageChange(cryptoFromDatabase.getTotalVolume(), cryptoAfterScrapping.getTotalVolume())).thenReturn(10f);
        when(smsService.sendSMS("$$$ CRYPTO-ALERT $$$ Bitcoin volume is rising: 10.0% in just 10 minutes!")).thenReturn(ResponseEntity.ok("$$$ CRYPTO-ALERT $$$ Bitcoin volume is rising: 10.0% in just 5 minutes!"));

        ResponseEntity<String> response = manageChangingDataService.manageIncreaseVolume(cryptoAfterScrapping, cryptoFromDatabase);
        Assertions.assertThat(response.getBody()).isEqualTo("$$$ CRYPTO-ALERT $$$ Bitcoin volume is rising: 10.0% in just 10 minutes!");
    }

}
