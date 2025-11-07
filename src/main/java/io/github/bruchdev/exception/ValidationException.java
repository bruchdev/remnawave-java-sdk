package io.github.bruchdev.exception;

import lombok.Getter;

import java.util.List;

public final class ValidationException extends Exception {

    @Getter
    private final Integer statusCode;
    @Getter
    private final List<ErrorResponse.FieldError> errors;

    public ValidationException(List<ErrorResponse.FieldError> errors) {
        super("Validation failed");
        this.statusCode = 400;
        this.errors = errors;
    }

    public ValidationException(ErrorResponse errorResponse) {
        this(errorResponse.errors());
    }

}
