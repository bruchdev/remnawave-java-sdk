package io.github.bruchdev.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class RemnawaveException extends Exception {
    /**
     * http status code
     */
    private final Integer statusCode;

    private final String message;

    private final List<FieldError> errors;

    protected RemnawaveException(String message, Integer statusCode, List<FieldError> errors) {
        super(message);
        this.statusCode = statusCode;
        this.message = message;
        this.errors = errors;
    }

    public record FieldError(
            String validation,
            String code,
            String message,
            List<String> path
    ) {
    }
}
