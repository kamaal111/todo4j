package io.kamaal.todo4j.shared.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.CONFLICT, reason="Conflict")
public class ConflictException extends BaseHTTPException {
  public ConflictException(String message) {
    super(message, HttpStatus.CONFLICT);
  }
}
