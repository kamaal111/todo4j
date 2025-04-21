package io.kamaal.todo4j.user.exception;

import io.kamaal.todo4j.shared.exception.NotFoundException;

public class UserNotFoundExceptionException extends NotFoundException {
    public UserNotFoundExceptionException() { super("User not found"); }
}
