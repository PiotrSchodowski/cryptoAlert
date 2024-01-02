package pl.schodowski.CryptoPriceAlert.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import pl.schodowski.CryptoPriceAlert.repo.Crypto;

import java.util.Arrays;
import java.util.List;

@Service
public class DataLoaderService implements ApplicationListener<ContextRefreshedEvent> {

    private final ApiService apiService;

    private final String cryptoList;

    public DataLoaderService(ApiService apiService, @Value("${crypto.list}") String cryptoList) {
        this.apiService = apiService;
        this.cryptoList = cryptoList;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        saveSelectedCryptoToDatabase(makeListOfCryptoFromApplicationProperties());
    }


    void saveSelectedCryptoToDatabase(List<String> symbolsFromProperties) {
        List<Crypto> cryptocurrencies = apiService.getCryptoListFromCoinGecko();

        Crypto cryptoToDatabase = new Crypto();

        for (String symbols : symbolsFromProperties) {

            for (Crypto crypto : cryptocurrencies) {

                if (crypto.getSymbol().equalsIgnoreCase(symbols)) {
                    cryptoToDatabase = crypto;
                }
            }
            System.out.println(cryptoToDatabase);
        }

    }

    private List<String> makeListOfCryptoFromApplicationProperties() {
        return Arrays.asList(cryptoList.split(","));
    }

}
