package io.github.bruchdev.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;


@Getter
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class ValidationException extends RuntimeException {

    private final Integer statusCode;
    private final String message;
    private final OffsetDateTime timestamp;
    private final List<ErrorResponse.FieldError> errors;

    public ValidationException(Integer statusCode,
                               String message,
                               OffsetDateTime timestamp,
                               List<ErrorResponse.FieldError> errors) {
        super(message);
        this.statusCode = 400;
        this.message = message;
        this.timestamp = timestamp;
        this.errors = errors;
    }

    public ValidationException(ErrorResponse errorResponse) {
        this(errorResponse.statusCode(), errorResponse.message(), OffsetDateTime.now(), errorResponse.errors());
    }

}
