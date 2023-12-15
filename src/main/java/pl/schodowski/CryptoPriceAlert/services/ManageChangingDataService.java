package pl.schodowski.CryptoPriceAlert.services;

import org.springframework.stereotype.Service;
import pl.schodowski.CryptoPriceAlert.entities.Crypto;

@Service
public class ManageChangingDataService {

    public void manageIncreasePrice(Crypto cryptoAfterScrapping, Crypto cryptoFromDatabase) {
        System.out.println("Price of "
                + cryptoFromDatabase.getName()
                + " has increased about: "
                + (cryptoAfterScrapping.getPrice()
                - cryptoFromDatabase.getPrice()));
    }

    public void manageDecreasePrice(Crypto cryptoAfterScrapping, Crypto cryptoFromDatabase) {
        System.out.println("Price of "
                + cryptoFromDatabase.getName()
                + " has decreased about: "
                + (cryptoFromDatabase.getPrice()
                - cryptoAfterScrapping.getPrice()));
    }

    public void manageNoChangePrice(Crypto cryptoAfterScrapping, Crypto cryptoFromDatabase) {
        System.out.println("Price of "
                + cryptoFromDatabase.getName()
                + " has not changed!");
    }
}
