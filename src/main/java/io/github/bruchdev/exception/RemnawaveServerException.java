package io.github.bruchdev.exception;

import java.util.List;

public class RemnawaveServerException extends RemnawaveException {

    public RemnawaveServerException(Integer statusCode, String message, List<FieldError> errors) {
        super(message, statusCode, errors);
    }

    public RemnawaveServerException(Integer statusCode, String message) {
        super(message, statusCode, null);
    }
}
