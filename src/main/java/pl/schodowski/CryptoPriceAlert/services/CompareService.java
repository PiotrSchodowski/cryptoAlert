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

    public void compareManager(Crypto cryptoAfterScrapping) {
        returnCryptoFromDatabase(cryptoAfterScrapping)
                .subscribe(
                        cryptoFromDatabase -> {
                            compareCryptoPrice(cryptoAfterScrapping, cryptoFromDatabase);
                        },
                        error -> {
                            throw new CryptoPriceAlertException("Error retrieving crypto from the database", true);
                        }
                );
    }


    public Mono<Crypto> returnCryptoFromDatabase(Crypto cryptoAfterScrapping) {
        return cryptoRepo.findByName(cryptoAfterScrapping.getName());
    }


    public void compareCryptoPrice(Crypto cryptoAfterScrapping, Crypto cryptoFromDatabase) {

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


    public void updateCryptoToDatabase(Crypto cryptoAfterScrapping) {
        returnCryptoFromDatabase(cryptoAfterScrapping)
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
