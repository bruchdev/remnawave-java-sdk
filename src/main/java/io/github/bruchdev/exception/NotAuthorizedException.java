package io.github.bruchdev.exception;

import lombok.Getter;

public final class NotAuthorizedException extends RuntimeException {

    @Getter
    private final int code;

    public NotAuthorizedException() {
        super("Not authorized");
        this.code = 401;
    }
}
