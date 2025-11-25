package io.github.bruchdev.exception;

public final class NotAuthenticatedException extends RemnawaveException {

    public NotAuthenticatedException() {
        super("Not authenticated", 403, null);
    }
}
