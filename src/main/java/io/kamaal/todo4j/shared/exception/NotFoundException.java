package io.kamaal.todo4j.shared.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Not Found")
public class NotFoundException extends BaseHTTPException {
    public NotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
