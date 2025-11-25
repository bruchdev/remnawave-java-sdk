package io.github.bruchdev.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RemnawaveErrorResponse(
        Integer statusCode,
        String message,
        List<RemnawaveException.FieldError> errors) {

    public record FieldError(
            String validation,
            String code,
            String message,
            List<String> path
    ) {
    }
}
