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

    public void getAssetPriceByName(String name) {

        Crypto crypto = new Crypto();
        String formattedName = name.replace(" ", "-");

        String url = "https://coinmarketcap.com/currencies/" + formattedName.toLowerCase();

        try{
            Document document = Jsoup.connect(url).get();

            Element priceByHTML = document.selectFirst("span.sc-f70bb44c-0.jxpCgO.base-text");
            Element marketCapByHTML = document.selectFirst("dd.sc-f70bb44c-0");

            float priceAfter = 0;
            BigDecimal marketCapAfter = BigDecimal.valueOf(0);

            if (priceByHTML != null) {
                String priceText = priceByHTML.text().replaceAll("[^\\d.,]+", "");
                priceText = priceText.replaceAll("(\\d),(\\d)", "$1$2");
                priceAfter = Float.parseFloat(priceText);
            }

            if (marketCapByHTML != null) {
                String marketCapText = marketCapByHTML.text();
                Pattern pattern = Pattern.compile("\\d+");
                Matcher matcher = pattern.matcher(marketCapText);
                StringBuilder numberBuilder = new StringBuilder();
                while (matcher.find()) {
                    numberBuilder.append(matcher.group());
                }
                String trimmedMarketCap = numberBuilder.toString().substring(3);
                marketCapAfter = new BigDecimal(trimmedMarketCap);
            }

//            System.out.println(name);
//            System.out.println("Price: " + priceAfter);
//            System.out.println("Market cap: " + marketCapAfter);

            crypto.setName(name);
            crypto.setPrice(priceAfter);
            crypto.setMarketCap(marketCapAfter.longValue());


            compareService.compareCrypto(crypto);


        }catch (IOException e) {
            throw new RuntimeException("Error fetching asset price for name " + name, e);
        }

    }
}
