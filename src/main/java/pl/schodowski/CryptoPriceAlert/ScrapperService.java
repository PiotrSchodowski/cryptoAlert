package pl.schodowski.CryptoPriceAlert;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ScrapperService {

    private final CompareService compareService;

    public void pushCryptoToUpdate(Crypto crypto) {

        String formattedName = crypto.getName().replace(" ", "-");
        String urlCoinMarket = "https://coinmarketcap.com/currencies/" + formattedName.toLowerCase();
        String urlCryptoSlate = "https://cryptoslate.com/coins/" + formattedName.toLowerCase();

        try {
            Document htmlCoinMarket = Jsoup.connect(urlCoinMarket).get();
            Document htmlCryptoSlate = Jsoup.connect(urlCryptoSlate).get();

            Element priceByHTML = htmlCoinMarket.selectFirst("span.sc-f70bb44c-0.jxpCgO.base-text");
            Element marketCapByHTML = htmlCoinMarket.selectFirst("dd.sc-f70bb44c-0");
            Element volume24hByHTML = htmlCryptoSlate.selectFirst("span.holepunch.holepunch-coin_24h_volume_usd");

//todo jezeli któryś element pusty to weź z drugiego źródła

            System.out.println(priceByHTML.text());
            System.out.println(marketCapByHTML.text());
            System.out.println(volume24hByHTML.text());

            if(priceByHTML != null && marketCapByHTML != null && volume24hByHTML != null) {
                crypto.setTotalVolume(extractVolume(volume24hByHTML.text()));
                crypto.setPrice(extractPrice(priceByHTML.text()));
                crypto.setMarketCap(extractMarketCap(marketCapByHTML.text()).longValue());
            }

            System.out.println(crypto);
            compareService.compareCrypto(crypto);


        } catch (IOException e) {
            throw new RuntimeException("Error fetching asset price for name " + crypto.getName(), e);
        }

    }


    public float extractPrice(String value) {
        String priceText = value.replaceAll("[^\\d.,]+", "");
        priceText = priceText.replaceAll("(\\d),(\\d)", "$1$2");
        return Float.parseFloat(priceText);
    }


    public float extractVolume(String value) {
        if (value.endsWith("B")) {
            String numberStr = value.substring(0, value.length() - 1);
            return Float.parseFloat(numberStr ) * 1000;
        }
        if (value.endsWith("M")) {
            String numberStr = value.substring(0, value.length() - 1);
            return Float.parseFloat(numberStr);
        }
        throw new IllegalArgumentException("Invalid input format: " + value);
    }


    public BigDecimal extractMarketCap(String value) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(value);
        StringBuilder numberBuilder = new StringBuilder();
        while (matcher.find()) {
            numberBuilder.append(matcher.group());
        }
        String trimmedMarketCap = numberBuilder.toString().substring(3);
        return new BigDecimal(trimmedMarketCap);
    }
}
