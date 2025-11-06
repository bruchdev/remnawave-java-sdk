package io.github.bruchdev.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        Integer statusCode,
        String message,
        List<FieldError> errors) {

    public record FieldError(
            String validation,
            String code,
            String message,
            List<String> path
    ) {
    }
}