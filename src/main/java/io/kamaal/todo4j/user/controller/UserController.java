package io.kamaal.todo4j.user.controller;

import io.kamaal.todo4j.user.model.UserPayload;
import io.kamaal.todo4j.user.model.UserResponse;
import io.kamaal.todo4j.user.service.UserService;
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
}
