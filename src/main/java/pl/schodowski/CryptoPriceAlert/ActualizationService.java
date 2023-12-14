package pl.schodowski.CryptoPriceAlert;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class ActualizationService {

    private final CryptoRepo cryptoRepo;
    private final ScrapperService scrapperService;

    public void actualizeCrypto() {

        Flux<Crypto> allCrypto = cryptoRepo.findAll();
        allCrypto.doOnNext(scrapperService::pushCryptoToUpdate).blockLast();

    }
}
