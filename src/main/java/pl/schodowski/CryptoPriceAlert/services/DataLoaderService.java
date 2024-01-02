package pl.schodowski.CryptoPriceAlert.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import pl.schodowski.CryptoPriceAlert.repo.Crypto;
import pl.schodowski.CryptoPriceAlert.repo.CryptoRepo;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

@Service
public class DataLoaderService implements ApplicationListener<ContextRefreshedEvent> {

    private final ApiService apiService;
    private final String cryptoList;
    private final CryptoRepo cryptoRepo;

    public DataLoaderService(ApiService apiService, @Value("${crypto.list}") String cryptoList, CryptoRepo cryptoRepo) {
        this.apiService = apiService;
        this.cryptoList = cryptoList;
        this.cryptoRepo = cryptoRepo;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        saveSelectedCryptoToDatabase(makeListOfCryptoFromApplicationProperties());
    }

    void saveSelectedCryptoToDatabase(List<String> symbolsFromProperties) {
        Flux<Crypto> cryptoFlux = Flux.fromIterable(apiService.getCryptoListFromCoinGecko())
                .filter(crypto -> symbolsFromProperties.contains(crypto.getSymbol().toUpperCase()));

        cryptoRepo.saveAll(cryptoFlux).subscribe();
    }

    private List<String> makeListOfCryptoFromApplicationProperties() {
        return Arrays.asList(cryptoList.split(","));
    }
}
