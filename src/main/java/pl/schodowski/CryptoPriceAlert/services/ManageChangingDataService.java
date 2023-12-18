package pl.schodowski.CryptoPriceAlert.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.schodowski.CryptoPriceAlert.repo.Crypto;

@Service
@RequiredArgsConstructor
public class ManageChangingDataService {

    private final CalculateService calculateService;
    private final SmsService smsService;

    @Value("${values.percentage_change}")
    float CHANGE_PERCENTAGE_VALUE;
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
            return ResponseEntity.ok("Price of "
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
                    + "% in just 10 minutes!";

            smsService.sendSMS(wholeMessage);
            return ResponseEntity.ok(wholeMessage);
        } else {
            return ResponseEntity.ok("Price of "
                    + cryptoFromDatabase.getName()
                    + " has not changed!");
        }
    }


    public ResponseEntity<String> manageIncreaseVolume(Crypto cryptoAfterScrapping, Crypto cryptoFromDatabase) {

        sizePriceOfChange = calculateService.calculatePercentageChange(
                cryptoFromDatabase.getTotalVolume(),
                cryptoAfterScrapping.getTotalVolume());

        if (sizePriceOfChange > CHANGE_PERCENTAGE_VALUE) {

            String wholeMessage = "$$$ CRYPTO-ALERT $$$ "
                    + cryptoFromDatabase.getName()
                    + " volume is rising: "
                    + sizePriceOfChange
                    + "% in just 10 minutes!";

            smsService.sendSMS(wholeMessage);
            return ResponseEntity.ok(wholeMessage);
        } else {
            return ResponseEntity.ok("Volume of "
                    + cryptoFromDatabase.getName()
                    + " has not changed!");
        }
    }

    public ResponseEntity<String> manageDecreaseVolume(Crypto cryptoAfterScrapping, Crypto cryptoFromDatabase) {

        sizePriceOfChange = calculateService.calculatePercentageChange(
                cryptoFromDatabase.getTotalVolume(),
                cryptoAfterScrapping.getTotalVolume());

        if (sizePriceOfChange < (-CHANGE_PERCENTAGE_VALUE)) {

            String wholeMessage = "$$$ CRYPTO-ALERT $$$ "
                    + cryptoFromDatabase.getName()
                    + " volume is falling: "
                    + sizePriceOfChange
                    + "% in just 10 minutes!";

            smsService.sendSMS(wholeMessage);
            return ResponseEntity.ok(wholeMessage);
        } else {
            return ResponseEntity.ok("Volume of "
                    + cryptoFromDatabase.getName()
                    + " has not changed!");
        }
    }


    public void manageNoChangePrice(Crypto cryptoFromDatabase) {
        System.out.println(cryptoFromDatabase.getName() + " NO CHANGE PRICE");
    }


    public void manageNoChangeVolume(Crypto cryptoFromDatabase) {
        System.out.println(cryptoFromDatabase.getName() + " NO CHANGE VOLUME");
    }
}
