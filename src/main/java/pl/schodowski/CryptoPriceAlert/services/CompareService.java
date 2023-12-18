package pl.schodowski.CryptoPriceAlert.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.schodowski.CryptoPriceAlert.exceptions.CryptoPriceAlertException;
import pl.schodowski.CryptoPriceAlert.repo.Crypto;
import pl.schodowski.CryptoPriceAlert.repo.CryptoRepo;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CompareService {

    private final CryptoRepo cryptoRepo;
    private final ManageChangingDataService manageChangingDataService;

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(CompareService.class);

    public void compareManager(Crypto cryptoAfterScrapping) {
        returnCryptoByNameFromDatabase(cryptoAfterScrapping)
                .subscribe(
                        cryptoFromDatabase -> {
                            compareCryptoPrice(cryptoAfterScrapping, cryptoFromDatabase);
                            compareCryptoVolume(cryptoAfterScrapping, cryptoFromDatabase);
                        },
                        error -> {
                            log.error("Error retrieving crypto from the database", error);
                            throw new CryptoPriceAlertException("Error retrieving crypto from the database", true);
                        }

                );
    }

    private void compareCryptoVolume(Crypto cryptoAfterScrapping, Crypto cryptoFromDatabase) {
        System.out.println(cryptoFromDatabase.getName() + " VOLUME BEFORE: " + cryptoFromDatabase.getTotalVolume() + " " + " AFTER: "+ cryptoAfterScrapping.getTotalVolume());

        if (cryptoFromDatabase.getTotalVolume() < cryptoAfterScrapping.getTotalVolume()) {
            manageChangingDataService.manageIncreaseVolume(cryptoAfterScrapping, cryptoFromDatabase);
        } else if (cryptoFromDatabase.getTotalVolume() > cryptoAfterScrapping.getTotalVolume()) {
            manageChangingDataService.manageDecreaseVolume(cryptoAfterScrapping, cryptoFromDatabase);
        } else {
            manageChangingDataService.manageNoChangeVolume(cryptoFromDatabase);
            return;
        }
        updateCryptoToDatabase(cryptoAfterScrapping);
    }


    public void compareCryptoPrice(Crypto cryptoAfterScrapping, Crypto cryptoFromDatabase) {
        System.out.println(cryptoFromDatabase.getName() + " PRICE BEFORE: " + cryptoFromDatabase.getPrice() + " " + " AFTER: "+ cryptoAfterScrapping.getPrice());

        if (cryptoFromDatabase.getPrice() < cryptoAfterScrapping.getPrice()) {
            manageChangingDataService.manageIncreasePrice(cryptoAfterScrapping, cryptoFromDatabase);
        } else if (cryptoFromDatabase.getPrice() > cryptoAfterScrapping.getPrice()) {
            manageChangingDataService.manageDecreasePrice(cryptoAfterScrapping, cryptoFromDatabase);
        } else {
            manageChangingDataService.manageNoChangePrice(cryptoFromDatabase);
            return;
        }
        updateCryptoToDatabase(cryptoAfterScrapping);
    }


    public Mono<Crypto> returnCryptoByNameFromDatabase(Crypto cryptoAfterScrapping) {
        return cryptoRepo.findByName(cryptoAfterScrapping.getName());
    }


    public void updateCryptoToDatabase(Crypto cryptoAfterScrapping) {
        returnCryptoByNameFromDatabase(cryptoAfterScrapping)
                .flatMap(cryptoFromDatabase -> {
                    cryptoFromDatabase.setSymbol(cryptoAfterScrapping.getSymbol());
                    cryptoFromDatabase.setPrice(cryptoAfterScrapping.getPrice());
                    cryptoFromDatabase.setMarketCap(cryptoAfterScrapping.getMarketCap());
                    cryptoFromDatabase.setTotalVolume(cryptoAfterScrapping.getTotalVolume());
                    return cryptoRepo.save(cryptoFromDatabase);
                })
                .doOnError(error -> {
                    throw new CryptoPriceAlertException("Error updating crypto in the database", true);
                })
                .subscribe();
    }
}
