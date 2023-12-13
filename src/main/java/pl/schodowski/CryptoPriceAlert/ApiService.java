package pl.schodowski.CryptoPriceAlert;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;

import java.util.Arrays;

@Service
public class ApiService {

    private final RestTemplate restTemplate;
    private final String apiUrl;

    public ApiService(RestTemplate restTemplate, @Value("${apiUrl.coinGecko}") String apiUrl) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
    }

    public Flux<Crypto> getCryptocurrenciesFromApi() {
        ResponseEntity<Crypto[]> response = restTemplate.exchange(apiUrl, HttpMethod.GET, null, Crypto[].class);
        return Flux.fromIterable(Arrays.asList(response.getBody()));
    }
}
