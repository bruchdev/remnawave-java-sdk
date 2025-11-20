package io.github.bruchdev.exception;

import java.util.List;

public class RemnawaveServerException extends RemnawaveException {

    public RemnawaveServerException(String message, Integer statusCode, List<FieldError> errors) {
        super(message, statusCode, errors);
    }

}
