package io.github.bruchdev.exception;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String identifier) {
        super("User not found for identifier: " + identifier);
    }
}
