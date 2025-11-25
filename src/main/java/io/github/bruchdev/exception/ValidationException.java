package io.github.bruchdev.exception;

import java.util.List;


public final class ValidationException extends RemnawaveException {

    public ValidationException(List<FieldError> errors) {
        super("Validation failed", 403, errors);
    }
}
