package pl.schodowski.CryptoPriceAlert.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class DataLoaderServiceTests {

    @Mock
    private DataLoaderService dataLoaderService;

    @Mock
    private ApiService apiService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnList() {
        System.out.println(apiService.getCryptoListFromCoinGecko());
    }
}
