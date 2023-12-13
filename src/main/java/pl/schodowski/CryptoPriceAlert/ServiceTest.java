package pl.schodowski.CryptoPriceAlert;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ServiceTest {

    private final CryptoRepo cryptoRepo;

    public Mono<Crypto> getCryptoBySymbol(String symbol) {
        return cryptoRepo.findBySymbol(symbol);
    }
}
