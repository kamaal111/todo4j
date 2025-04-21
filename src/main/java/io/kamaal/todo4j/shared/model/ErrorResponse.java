package io.kamaal.todo4j.shared.model;

import org.springframework.http.HttpStatus;

public record ErrorResponse(Long status, String message, String details) {
    public static ErrorResponse fromHTTPStatus(HttpStatus status, String details) {
        return new ErrorResponse((long) status.value(), status.getReasonPhrase(), details);
    }
}
