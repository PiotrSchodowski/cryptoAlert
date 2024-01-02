package pl.schodowski.CryptoPriceAlert.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.schodowski.CryptoPriceAlert.repo.Crypto;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class ApiService {

    private final RestTemplate restTemplate;

    private final String url;

    public ApiService(RestTemplate restTemplate, @Value("${apiUrl.coinGecko}") String url) {
        this.restTemplate = restTemplate;
        this.url = url;
    }

    public List<Crypto> getCryptoListFromCoinGecko() {
        ResponseEntity<Crypto[]> response = restTemplate.exchange(url, HttpMethod.GET, null, Crypto[].class);
        return Arrays.asList(Objects.requireNonNull(response.getBody()));
    }
}
