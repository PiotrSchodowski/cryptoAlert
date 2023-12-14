package pl.schodowski.CryptoPriceAlert.unitTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import pl.schodowski.CryptoPriceAlert.ScrapperService;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ScrapperTests {

    @InjectMocks
    private ScrapperService scrapperService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldExtractPriceFromTextOfHtml() {
        String htmlText = "$43,023.56";
        scrapperService.extractPrice(htmlText);
        assertThat(scrapperService.extractPrice(htmlText)).isEqualTo(43023.56f);

    }

    @Test
    void shouldExtractVolumeFromTextOfHtml() {
        String htmlText = "26.93B";
        scrapperService.extractVolume(htmlText);
        assertThat(scrapperService.extractVolume(htmlText)).isEqualTo(26930.0f);
    }

    @Test
    void shouldExtractMarketCapFromTextOfHtml() {
        String htmlText = "5.06% $842,925,195,444";
        BigDecimal expectedMarketCap = new BigDecimal("8.42925195444E11");
        BigDecimal actualMarketCap = scrapperService.extractMarketCap(htmlText);
        assertThat(actualMarketCap).isEqualByComparingTo(expectedMarketCap);
    }
}
