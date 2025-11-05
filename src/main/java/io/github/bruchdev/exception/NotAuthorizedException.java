package io.github.bruchdev.exception;

public class NotAuthorizedException extends RuntimeException {
    public NotAuthorizedException() {
        super("Not authorized");
    }
}
