package pl.schodowski.CryptoPriceAlert.unitTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import pl.schodowski.CryptoPriceAlert.services.CalculateService;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
}
