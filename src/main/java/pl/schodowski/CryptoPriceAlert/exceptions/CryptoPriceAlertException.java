package pl.schodowski.CryptoPriceAlert.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CryptoPriceAlertException extends RuntimeException {
    private final String message;
    private final boolean error;

    public CryptoPriceAlertException(String message) {
        this.message = message;
        this.error = false;
    }
}
