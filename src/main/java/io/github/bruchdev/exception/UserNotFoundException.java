package io.github.bruchdev.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String id) {
        super(id);
    }
}
