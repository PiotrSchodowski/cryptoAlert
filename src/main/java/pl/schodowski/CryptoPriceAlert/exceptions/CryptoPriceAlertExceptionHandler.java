package pl.schodowski.CryptoPriceAlert.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CryptoPriceAlertExceptionHandler {

    @ExceptionHandler(CryptoPriceAlertException.class)
    public ResponseEntity<String> handleCryptoPriceAlertException(CryptoPriceAlertException ex) {
        return ResponseEntity.status(400).body(ex.getMessage());
    }

}
