package io.kamaal.todo4j.user.controller;

import io.kamaal.todo4j.shared.model.ErrorResponse;
import io.kamaal.todo4j.user.exception.UserNotFoundException;
import io.kamaal.todo4j.user.model.User;
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
    public User create(@RequestBody User payload) {
        return service.create(payload.getUsername(), payload.getPassword());
    }

    @GetMapping("/{username}")
    public User byUsername(@PathVariable String username) {
        return service.findByUsername(username);
    }

    @ResponseStatus( HttpStatus.NOT_FOUND )
    @ExceptionHandler(value = UserNotFoundException.class)
    public ErrorResponse handleNotFound(HttpServletRequest req, UserNotFoundException e) {
        return new ErrorResponse((long) HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), e.getMessage());
    }
}
