package pl.schodowski.CryptoPriceAlert;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {

    private final ActualizationService actualizationService;

    @Scheduled(fixedRate = 100000)
    public void actualizeCrypto() {
        actualizationService.actualizeCrypto();
    }
}
