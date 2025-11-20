package io.github.bruchdev.exception;

import java.util.List;

public class ResourceNotFoundException extends RemnawaveException {

    public ResourceNotFoundException(String message) {
        super(message, 404, null);
    }

}
