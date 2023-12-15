package pl.schodowski.CryptoPriceAlert.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.schodowski.CryptoPriceAlert.entities.Crypto;

@Service
@RequiredArgsConstructor
public class ManageChangingDataService {

    private final CalculateService calculateService;

    public void manageIncreasePrice(Crypto cryptoAfterScrapping, Crypto cryptoFromDatabase) {
        System.out.println("Price of "
                + cryptoFromDatabase.getName()
                + " has increased about: "
                + (cryptoAfterScrapping.getPrice()
                - cryptoFromDatabase.getPrice())
                + " (" + calculateService.calculatePercentageChange(cryptoFromDatabase.getPrice(), cryptoAfterScrapping.getPrice()) + "%)");

    }

    public void manageDecreasePrice(Crypto cryptoAfterScrapping, Crypto cryptoFromDatabase) {
        System.out.println("Price of "
                + cryptoFromDatabase.getName()
                + " has decreased about: "
                + (cryptoFromDatabase.getPrice()
                - cryptoAfterScrapping.getPrice())
                + " (" + calculateService.calculatePercentageChange(cryptoFromDatabase.getPrice(), cryptoAfterScrapping.getPrice()) + "%)"
        );
    }

    public void manageNoChangePrice(Crypto cryptoAfterScrapping, Crypto cryptoFromDatabase) {
        System.out.println("Price of "
                + cryptoFromDatabase.getName()
                + " has not changed!");
    }
}
