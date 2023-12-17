package pl.schodowski.CryptoPriceAlert.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.schodowski.CryptoPriceAlert.exceptions.CryptoPriceAlertException;
import pl.schodowski.CryptoPriceAlert.repo.Crypto;

@Service
@RequiredArgsConstructor
public class ManageChangingDataService {

    private final CalculateService calculateService;
    private final SmsService smsService;

    float CHANGE_PERCENTAGE_VALUE = 0.1f;   //todo do application.properties
    float sizePriceOfChange;

    public ResponseEntity<String> manageIncreasePrice(Crypto cryptoAfterScrapping, Crypto cryptoFromDatabase) {

        sizePriceOfChange = calculateService.calculatePercentageChange(
                cryptoFromDatabase.getPrice(),
                cryptoAfterScrapping.getPrice());

        if (sizePriceOfChange > CHANGE_PERCENTAGE_VALUE) {

            String wholeMessage = "$$$ CRYPTO-ALERT $$$ "
                    + cryptoFromDatabase.getName()
                    + " price is rising: "
                    + sizePriceOfChange
                    + "% in just 5 minutes!";
            smsService.sendSMS(wholeMessage);
            return ResponseEntity.ok(wholeMessage);
        } else {
            throw new CryptoPriceAlertException("Price of "
                    + cryptoFromDatabase.getName()
                    + " has not changed!");
        }
    }


    public ResponseEntity<String> manageDecreasePrice(Crypto cryptoAfterScrapping, Crypto cryptoFromDatabase) {

        sizePriceOfChange = calculateService.calculatePercentageChange(
                cryptoFromDatabase.getPrice(),
                cryptoAfterScrapping.getPrice());

        if (sizePriceOfChange < (-CHANGE_PERCENTAGE_VALUE)) {

            String wholeMessage = "$$$ CRYPTO-ALERT $$$ "
                    + cryptoFromDatabase.getName()
                    + " price is falling: "
                    + sizePriceOfChange
                    + "% in just 5 minutes!";

            smsService.sendSMS(wholeMessage);
            return ResponseEntity.ok(wholeMessage);
        }
        else {
            throw new CryptoPriceAlertException("Price of "
                    + cryptoFromDatabase.getName()
                    + " has not changed!");
        }
    }


    public ResponseEntity<String> manageNoChangePrice(Crypto cryptoFromDatabase) {
        return ResponseEntity.ok("Price of "
                + cryptoFromDatabase.getName()
                + " has not changed!");
    }
}
