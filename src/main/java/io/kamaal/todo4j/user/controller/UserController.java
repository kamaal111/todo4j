package io.kamaal.todo4j.user.controller;

import io.kamaal.todo4j.user.exception.UserBadPayloadException;
import io.kamaal.todo4j.user.model.UserPayload;
import io.kamaal.todo4j.user.model.UserResponse;
import io.kamaal.todo4j.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus( HttpStatus.CREATED )
    public UserResponse create(@RequestBody UserPayload payload) throws UserBadPayloadException {
        var username = payload.username().orElseThrow(UserBadPayloadException::new);
        var password = payload.password().orElseThrow(UserBadPayloadException::new);
        var user = service.create(username, password);

        return UserResponse.fromUser(user);
    }

    @GetMapping("/{username}")
    public UserResponse byUsername(@PathVariable String username) {
        var user = service.findByUsername(username);

        return UserResponse.fromUser(user);
    }
}
