package io.kamaal.todo4j.user.controller;

import io.kamaal.todo4j.shared.model.ErrorResponse;
import io.kamaal.todo4j.user.exception.UserBadPayloadException;
import io.kamaal.todo4j.user.exception.UserNotFoundExceptionException;
import io.kamaal.todo4j.user.model.UserPayload;
import io.kamaal.todo4j.user.model.UserResponse;
import io.kamaal.todo4j.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService service;

    @PostMapping
    @ResponseStatus( HttpStatus.CREATED )
    public UserResponse create(@RequestBody UserPayload payload) {
        var user = service.create(payload.username(), payload.password());

        return UserResponse.fromUser(user);
    }

    @GetMapping("/{username}")
    public UserResponse byUsername(@PathVariable String username) {
        var user = service.findByUsername(username);

        return UserResponse.fromUser(user);
    }

    @ResponseStatus( HttpStatus.NOT_FOUND )
    @ExceptionHandler(value = UserNotFoundExceptionException.class)
    public ErrorResponse handleNotFound(HttpServletRequest req, UserNotFoundExceptionException e) {
        return new ErrorResponse((long) HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), e.getMessage());
    }

    @ResponseStatus( HttpStatus.BAD_REQUEST )
    @ExceptionHandler(value = UserBadPayloadException.class)
    public ErrorResponse handleBadRequest(HttpServletRequest req, UserBadPayloadException e) {
        return new ErrorResponse((long) HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage());
    }

    @ResponseStatus( HttpStatus.INTERNAL_SERVER_ERROR )
    @ExceptionHandler(value = RuntimeException.class)
    public ErrorResponse handleRuntime(HttpServletRequest req, RuntimeException e) {
        return new ErrorResponse((long) HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage());
    }
}
