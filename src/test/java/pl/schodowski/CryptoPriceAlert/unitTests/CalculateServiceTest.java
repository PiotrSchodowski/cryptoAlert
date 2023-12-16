package pl.schodowski.CryptoPriceAlert.unitTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import pl.schodowski.CryptoPriceAlert.services.CalculateService;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculateServiceTest {

    @InjectMocks
    private CalculateService calculateService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCalculatePercentageOfValueChange() {
        float value1 = 1000;
        float value2 = 1100;
        float expected = 10;

        float result = calculateService.calculatePercentageChange(value1, value2);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void shouldCalculatePercentageOfValueChangeInLangeAmounts() {
        float value1 = 41000;
        float value2 = 42000;
        float expected = 2.44f;

        float result = calculateService.calculatePercentageChange(value1, value2);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void shouldCalculatePercentageOfValueChangeInLangeDecimalValue() {
        float value1 = 0.00041f;
        float value2 = 0.00042f;
        float expected = 2.44f;

        float result = calculateService.calculatePercentageChange(value1, value2);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void shouldConvertToBigDecimal() {
        float floatValue = 123.45f;
        BigDecimal expectedBigDecimal = BigDecimal.valueOf(floatValue);

        BigDecimal actualBigDecimal = calculateService.convertToBigDecimal(floatValue);
        assertEquals(expectedBigDecimal, actualBigDecimal, "Conversion from float to BigDecimal failed.");
    }
}
