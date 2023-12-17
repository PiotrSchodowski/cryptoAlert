package pl.schodowski.CryptoPriceAlert.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.schodowski.CryptoPriceAlert.repo.Crypto;

@Service
@RequiredArgsConstructor
public class ManageChangingDataService {

    private final CalculateService calculateService;
    private final SmsService smsService;

    float CHANGE_PERCENTAGE_VALUE = 0.1f;   //todo do application.properties
    float sizePriceOfChange;

    public void manageIncreasePrice(Crypto cryptoAfterScrapping, Crypto cryptoFromDatabase) {

        sizePriceOfChange = calculateService.calculatePercentageChange(cryptoFromDatabase.getPrice(), cryptoAfterScrapping.getPrice());
        if (sizePriceOfChange > CHANGE_PERCENTAGE_VALUE) {
            System.out.println("CRYPTO-ALERT WAS SEND!");
            smsService.sendSMS("$$$ CRYPTO-ALERT $$$   "
                    + cryptoFromDatabase.getName()
                    + " price is rising: "
                    + sizePriceOfChange
                    + "% in just 5 minutes!");
        }

    }


    public void manageDecreasePrice(Crypto cryptoAfterScrapping, Crypto cryptoFromDatabase) {

        sizePriceOfChange = calculateService.calculatePercentageChange(cryptoFromDatabase.getPrice(), cryptoAfterScrapping.getPrice());
        if (sizePriceOfChange < (-CHANGE_PERCENTAGE_VALUE)) {
            System.out.println("CRYPTO-ALERT WAS SEND!");
            smsService.sendSMS("$$$ CRYPTO-ALERT $$$    "
                    + cryptoFromDatabase.getName()
                    + " price is falling: "
                    + sizePriceOfChange
                    + "% in just 5 minutes!");
        }
    }


    public void manageNoChangePrice(Crypto cryptoFromDatabase) {
        System.out.println("Price of "
                + cryptoFromDatabase.getName()
                + " has not changed!");
    }
}
