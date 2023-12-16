package pl.schodowski.CryptoPriceAlert.services;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class CalculateService {

    public float calculatePercentageChange(float priceSmaller, float priceBigger) {
        BigDecimal resultFromDivision = convertToBigDecimal(priceBigger - priceSmaller)
                .divide(convertToBigDecimal(priceSmaller),
                        4, RoundingMode.HALF_UP);
        return resultFromDivision.multiply(convertToBigDecimal(100)).floatValue();

    }

    public BigDecimal convertToBigDecimal(float value) {
        return BigDecimal.valueOf(value);
    }


}
