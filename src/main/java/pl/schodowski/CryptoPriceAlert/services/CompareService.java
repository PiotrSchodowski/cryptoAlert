package pl.schodowski.CryptoPriceAlert.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.schodowski.CryptoPriceAlert.entities.Crypto;
import pl.schodowski.CryptoPriceAlert.entities.CryptoRepo;
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
                            System.err.println("Error retrieving crypto from the database: " + error.getMessage());
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
            manageChangingDataService.manageNoChangePrice(cryptoAfterScrapping, cryptoFromDatabase);
        }

        updateCryptoToDatabase(cryptoAfterScrapping);
    }

    public void updateCryptoToDatabase(Crypto cryptoAfterScrapping) {
        returnCryptoFromDatabase(cryptoAfterScrapping)
                .subscribe(
                        cryptoFromDatabase -> {
                            cryptoFromDatabase.setSymbol(cryptoAfterScrapping.getSymbol());
                            cryptoFromDatabase.setPrice(cryptoAfterScrapping.getPrice());
                            cryptoFromDatabase.setMarketCap(cryptoAfterScrapping.getMarketCap());
                            cryptoFromDatabase.setTotalVolume(cryptoAfterScrapping.getTotalVolume());

                            cryptoRepo.save(cryptoFromDatabase)
                                    .subscribe(
                                            savedCrypto -> {
                                                System.out.println("Updated data in the database for " + savedCrypto.getName());
                                            },
                                            error -> {
                                                System.err.println("Error updating data in the database: " + error.getMessage());
                                            }
                                    );
                        },
                        error -> {
                            System.err.println("Error retrieving crypto from the database: " + error.getMessage());
                        }
                );
    }
}
