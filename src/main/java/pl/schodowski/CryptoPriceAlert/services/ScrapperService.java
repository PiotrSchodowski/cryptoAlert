package pl.schodowski.CryptoPriceAlert.services;


import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import pl.schodowski.CryptoPriceAlert.repo.Crypto;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ScrapperService {

    private final CompareService compareService;

    public void pushCryptoByNameToUpdate(Crypto crypto) {

        String urlCoinMarket = "https://coinmarketcap.com/currencies/" + formatName(crypto.getName());
        String urlCryptoSlate = "https://cryptoslate.com/coins/" + formatName(crypto.getName());
        String urlLiveCoinWatch = "https://www.livecoinwatch.com/price/" + crypto.getName() + "-" + crypto.getSymbol().toUpperCase();

        Element priceByHTML = getPriceByHTML(urlCoinMarket, urlCryptoSlate);
        Element marketCapByHTML = getMarketCapByHTML(urlCoinMarket);
        Element volume24hByLiveCoinWatch = getVolume24hByLiveCoinWatch(urlLiveCoinWatch);

        crypto.setTotalVolume(extractVolume(volume24hByLiveCoinWatch.text()));
        crypto.setPrice(extractPrice(priceByHTML.text()));
        crypto.setMarketCap(extractMarketCap(marketCapByHTML.text()).longValue());

        compareService.compareManager(crypto);
    }


    String getForTest(String url) {
        try {
            Document document = Jsoup.connect(url).get();
            Element element = document.selectFirst("span.price.no-grow");

            Elements elements = document.select("span.price.no-grow");
            if (elements.size() >= 2) {
                element = elements.get(1);
            }

            System.out.println(element.text());
            return element.text();

        } catch (IOException e) {
            throw new RuntimeException("Error fetching asset price for urls", e);
        }
    }


    Element getVolume24hByLiveCoinWatch(String url) {
        try {
            Document document = Jsoup.connect(url).get();
            Element element = document.selectFirst("span.price.no-grow");

            Elements elements = document.select("span.price.no-grow");
            if (elements.size() >= 2) {
                element = elements.get(1);
            }
            return element;

        } catch (IOException e) {
            throw new RuntimeException("Error fetching asset price for urls", e);
        }

    }


    Element getPriceByHTML(String urlCoinMarket, String urlCryptoSlate) {
        try {
            Document html = Jsoup.connect(urlCoinMarket).get();
            return html.selectFirst("span.sc-f70bb44c-0.jxpCgO.base-text");
        } catch (IOException e) {
            try {
                Document html = Jsoup.connect(urlCryptoSlate).get();
                return html.selectFirst("span.holepunch.holepunch-coin_price_usd");
            } catch (IOException ioException) {
                throw new RuntimeException("Error fetching asset price for urls", ioException);
            }
        }
    }


    Element getMarketCapByHTML(String urlCoinMarket) {
        try {
            Document html = Jsoup.connect(urlCoinMarket).get();
            return html.selectFirst("dd.sc-f70bb44c-0");
        } catch (IOException e) {
            throw new RuntimeException("Error fetching asset price for urls", e);
        }
    }


    Element getVolume24hByHTML(String urlCryptoSlate) {
        try {
            Document html = Jsoup.connect(urlCryptoSlate).get();
            return html.selectFirst("span.holepunch.holepunch-coin_24h_volume_usd");
        } catch (IOException e) {
            throw new RuntimeException("Error fetching asset price for urls", e);
        }
    }


    float extractPrice(String value) {
        String priceText = value.replaceAll("[^\\d.,]+", "");
        priceText = priceText.replaceAll("(\\d),(\\d)", "$1$2");
        return Float.parseFloat(priceText);
    }


    float extractVolume(String value) {
        if (value.endsWith("B")) {
            String numberStr = value.substring(1, value.length() - 1);
            return Float.parseFloat(numberStr) * 1000;
        } else if (value.endsWith("M")) {
            String numberStr = value.substring(1, value.length() - 1);
            return Float.parseFloat(numberStr);
        } else {
            throw new IllegalArgumentException("Invalid input format: " + value);
        }
    }


    BigDecimal extractMarketCap(String value) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(value);
        StringBuilder numberBuilder = new StringBuilder();
        while (matcher.find()) {
            numberBuilder.append(matcher.group());
        }
        String trimmedMarketCap = numberBuilder.substring(3);
        return new BigDecimal(trimmedMarketCap);
    }


    String formatName(String name) {
        return name.replace(" ", "-").toLowerCase();
    }
}


