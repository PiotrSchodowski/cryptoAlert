package pl.schodowski.CryptoPriceAlert;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompareService {

    private final CryptoRepo cryptoRepo;

    public void compareCrypto(Crypto cryptoAfterScrapping){

        cryptoRepo.findByName(cryptoAfterScrapping.getName())
                .subscribe(cryptoBeforeScrapping -> {
                    if (cryptoBeforeScrapping.getPrice() < cryptoAfterScrapping.getPrice()) {
                        System.out.println("Price of " + cryptoBeforeScrapping.getName() + " has increased!");
                    } else if (cryptoBeforeScrapping.getPrice() > cryptoAfterScrapping.getPrice()) {
                        System.out.println("Price of " + cryptoBeforeScrapping.getName() + " has decreased!");
                    } else {
                        System.out.println("Price of " + cryptoBeforeScrapping.getName() + " has not changed!");
                    }
                });

    }
}
