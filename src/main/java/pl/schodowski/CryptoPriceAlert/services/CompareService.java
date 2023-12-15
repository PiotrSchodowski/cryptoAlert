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

    public void compareManager(Crypto cryptoAfterScrapping){
        returnCryptoFromDatabase(cryptoAfterScrapping)
                .subscribe(
                        cryptoFromDatabase -> {
                            compareCrypto(cryptoAfterScrapping, cryptoFromDatabase);
                        },
                        error -> {
                            System.err.println("Error retrieving crypto from the database: " + error.getMessage());
                        }
                );
    }

    public Mono<Crypto> returnCryptoFromDatabase(Crypto cryptoAfterScrapping){
        return cryptoRepo.findByName(cryptoAfterScrapping.getName());
    }

    public void updateCryptoToDatabase(Crypto cryptoAfterScrapping){

    }

    public void compareCrypto(Crypto cryptoAfterScrapping, Crypto cryptoFromDatabase){

    }




}
