package io.kamaal.todo4j.user.exception;

import io.kamaal.todo4j.shared.exception.ConflictException;

public class UserAlreadyExistsException extends ConflictException {
    public UserAlreadyExistsException() {
        super("User already exists");
    }
}
