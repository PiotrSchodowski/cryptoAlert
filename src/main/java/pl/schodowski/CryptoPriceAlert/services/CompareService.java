package pl.schodowski.CryptoPriceAlert.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.schodowski.CryptoPriceAlert.entities.Crypto;
import pl.schodowski.CryptoPriceAlert.entities.CryptoRepo;

@Service
@RequiredArgsConstructor
public class CompareService {

    private final CryptoRepo cryptoRepo;

    public void compareManager(Crypto cryptoAfterScrapping){

    }

//    public Crypto returnCryptoFromDatabase(Crypto cryptoAfterScrapping){
//        cryptoRepo.findByName(cryptoAfterScrapping.getName()).
//    }

    public void updateCryptoToDatabase(Crypto cryptoAfterScrapping){

    }

    public void compareCrypto(Crypto cryptoAfterScrapping, Crypto cryptoFromDatabase){

    }




}
