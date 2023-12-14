package pl.schodowski.CryptoPriceAlert.unitTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import pl.schodowski.CryptoPriceAlert.entities.Crypto;
import pl.schodowski.CryptoPriceAlert.services.ScrapperService;

import org.jsoup.nodes.Element;
import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class ScrapperTests {

    String urlCoinMarket = "https://coinmarketcap.com/currencies/bitcoin";
    String urlCryptoSlateDOT = "https://cryptoslate.com/coins/polkadot/";
    String urlCryptoSlate = "https://cryptoslate.com/coins/bitcoin";
    String urlWrong = "https://coinmarketcap.com/WRONG";

    @InjectMocks
    private ScrapperService scrapperService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnTrueWhenEntityIsOk(){

        Crypto crypto = new Crypto();
        crypto.setName("Bitcoin");
        assertTrue(scrapperService.pushCryptoToUpdate(crypto));
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
    void shouldReturnExceptionWhenDocumentNotContainSpecialCharacters(){

        assertThrows(IllegalArgumentException.class,() -> scrapperService.extractVolume("123456789"));

    }

    @Test
    void shouldExtractMarketCapFromTextOfHtml() {

        String htmlText = "5.06% $842,925,195,444";
        BigDecimal expectedMarketCap = new BigDecimal("8.42925195444E11");
        BigDecimal actualMarketCap = scrapperService.extractMarketCap(htmlText);
        assertThat(actualMarketCap).isEqualByComparingTo(expectedMarketCap);
    }

    @Test
    void shouldReturnTextWithPriceWhenAllUrlsRight(){

        Element textWithPrice = scrapperService.getPriceByHTML(urlCoinMarket, urlCryptoSlate);
        System.out.println(textWithPrice.text());

        assertThat(textWithPrice.text()).contains("$");
        assertThat(textWithPrice.text()).contains(".");
        assertThat(textWithPrice.text().length()).isEqualTo(10);
    }

    @Test
    void shouldReturnTextWithPriceWhenFirstUrlWrong(){

        Element textWithPrice = scrapperService.getPriceByHTML(urlCoinMarket, urlCryptoSlate);
        System.out.println(textWithPrice.text());

        assertThat(textWithPrice.text()).contains("$");
        assertThat(textWithPrice.text()).contains(".");
        assertThat(textWithPrice.text().length()).isEqualTo(10);
    }

    @Test
    void shouldReturnExceptionWhenBothUrlsWrong(){

        assertThrows(RuntimeException.class, () -> scrapperService.getPriceByHTML(urlWrong, urlWrong));
    }

    @Test
    void shouldReturnTextWithMarketCapWhenUrlRight(){

        Element textWithMarketCap = scrapperService.getMarketCapByHTML(urlCoinMarket);
        System.out.println(textWithMarketCap.text());

        assertThat(textWithMarketCap.text()).contains("$");
        assertThat(textWithMarketCap.text()).contains(".");
        assertThat(textWithMarketCap.text().length()).isGreaterThan(10);
    }

    @Test
    void shouldReturnExceptionWhenUrlMarketCapIsWrong(){

        assertThrows(RuntimeException.class, () -> scrapperService.getMarketCapByHTML(urlWrong));
    }

    @Test
    void shouldReturnTextWithVolume24hWhenUrlRightWithBigVolume(){

        Element textWithVolume24h = scrapperService.getVolume24hByHTML(urlCryptoSlate);
        System.out.println(textWithVolume24h.text());

        assertThat(textWithVolume24h.text()).contains("B");
        assertThat(textWithVolume24h.text()).contains(".");
        assertThat(textWithVolume24h.text().length()).isGreaterThan(4);
        assertThat(textWithVolume24h.text().length()).isLessThan(8);

    }

    @Test
    void shouldReturnTextWithVolume24hWhenUrlRightWithSmallerVolume(){

        Element textWithVolume24hDOT = scrapperService.getVolume24hByHTML(urlCryptoSlateDOT);
        System.out.println(textWithVolume24hDOT.text());

        assertThat(textWithVolume24hDOT.text()).contains("M");
        assertThat(textWithVolume24hDOT.text()).contains(".");
        assertThat(textWithVolume24hDOT.text().length()).isGreaterThan(6);

    }

    @Test
    void shouldReturnExceptionWhenUrlVolumeIsWrong(){

        assertThrows(RuntimeException.class, () -> scrapperService.getVolume24hByHTML(urlWrong));
    }

    @Test
    void shouldReturnFormattedName(){

        String name = "Bitcoin Cash";
        String expectedName = "bitcoin-cash";
        String actualName = scrapperService.formatName(name);
        assertThat(actualName).isEqualTo(expectedName);
    }
}
