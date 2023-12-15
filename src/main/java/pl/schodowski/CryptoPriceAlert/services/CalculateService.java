package pl.schodowski.CryptoPriceAlert.services;

import org.springframework.stereotype.Service;

@Service
public class CalculateService {

    public float calculatePercentageChange(float priceBefore, float priceAfter) {
        return ((priceAfter - priceBefore) / priceBefore) * 100;
    }
}
