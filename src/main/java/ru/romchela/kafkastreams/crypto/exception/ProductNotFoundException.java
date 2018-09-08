package ru.romchela.kafkastreams.crypto.exception;

import java.text.MessageFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * If we haven't got any price information about particular product, this exception will be thrown
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Product not found")
public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(final String product) {
        super(MessageFormat.format("No information about product \"{0}\"", product));
    }
}
