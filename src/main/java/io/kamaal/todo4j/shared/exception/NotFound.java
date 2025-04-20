package io.kamaal.todo4j.shared.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Not Found")
public class NotFound extends RuntimeException {
    public NotFound(String message) {
        super(message);
    }
}
