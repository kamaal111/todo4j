package io.kamaal.todo4j.user.exception;

import io.kamaal.todo4j.shared.exception.BadRequestException;

public class UserBadPayloadException extends BadRequestException {
    public UserBadPayloadException() {
        super("Invalid user payload provided");
    }
}
