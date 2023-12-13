package pl.schodowski.CryptoPriceAlert;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final ApiService apiService;
    private final ServiceTest serviceTest;

    @GetMapping("/test")
    public Flux<Crypto> getCryptocurrenciesFromApi() {
        return apiService.getCryptocurrenciesFromApi();
    }

    @GetMapping("/{symbol}")
    public Mono<Crypto> getCryptoBySymbol(@PathVariable String symbol) {
        return serviceTest.getCryptoBySymbol(symbol);
    }
}
