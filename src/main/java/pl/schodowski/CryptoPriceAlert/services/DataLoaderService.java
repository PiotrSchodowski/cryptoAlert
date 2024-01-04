package pl.schodowski.CryptoPriceAlert.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import pl.schodowski.CryptoPriceAlert.repo.Crypto;
import pl.schodowski.CryptoPriceAlert.repo.CryptoRepo;
import reactor.core.publisher.Mono;

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

        List<Crypto> cryptoList = apiService.getCryptoListFromCoinGecko();

        for (String symbol : symbolsFromProperties) {

            cryptoList.stream()
                    .filter(crypto -> symbol.equalsIgnoreCase(crypto.getSymbol()))
                    .findFirst()
                    .ifPresent(firstCrypto -> {
                        Mono<Crypto> cryptoMono = Mono.just(firstCrypto);
                        cryptoMono.subscribe(crypto -> {
                            System.out.println("Symbol: " + crypto.getSymbol());
                            System.out.println("Price: " + crypto.getPrice());
                            System.out.println("Market Cap: " + crypto.getMarketCap());
                            crypto.setTotalVolume(crypto.getTotalVolume() / 1000000);
                            System.out.println("Total Volume: " + crypto.getTotalVolume());

                            cryptoRepo.save(crypto).subscribe(savedCrypto -> {
                                System.out.println("Crypto saved: " + savedCrypto);
                            });
                        });
                    });
        }
    }

    private List<String> makeListOfCryptoFromApplicationProperties() {
        return Arrays.asList(cryptoList.split(","));
    }
}
