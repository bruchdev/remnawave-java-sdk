package io.github.bruchdev.exception;

import java.util.List;

public class RemnawaveServerException extends RemnawaveException {

    public RemnawaveServerException(String message, List<FieldError> errors) {
        super(message, 500, errors);
    }

    public RemnawaveServerException(String message) {
        super(message, 500, null);
    }
}
