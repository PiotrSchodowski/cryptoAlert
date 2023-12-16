package pl.schodowski.CryptoPriceAlert.services;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.schodowski.CryptoPriceAlert.repo.Crypto;
import pl.schodowski.CryptoPriceAlert.repo.CryptoRepo;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class ActualizationService {

    private final CryptoRepo cryptoRepo;
    private final ScrapperService scrapperService;

    @Scheduled(fixedRate = 500000)
    public void actualizeCrypto() {

        Flux<Crypto> allCrypto = cryptoRepo.findAll();
        allCrypto.doOnNext(scrapperService::pushCryptoToUpdate).blockLast();
    }
}
