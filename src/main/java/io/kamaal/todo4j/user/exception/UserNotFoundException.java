package io.kamaal.todo4j.user.exception;

import io.kamaal.todo4j.shared.exception.NotFound;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="User not found")
public class UserNotFoundException extends NotFound {
    public UserNotFoundException() { super("User not found"); }
}
