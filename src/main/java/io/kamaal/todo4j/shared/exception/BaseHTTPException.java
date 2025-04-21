package io.kamaal.todo4j.shared.exception;

import org.springframework.http.HttpStatus;

public class BaseHTTPException extends RuntimeException {
    private final HttpStatus status;

    public BaseHTTPException(String message, HttpStatus status) {
      super(message);
      this.status = status;
    }

  public HttpStatus getStatus() {
    return status;
  }
}
