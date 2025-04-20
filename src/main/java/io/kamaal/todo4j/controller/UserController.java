package io.kamaal.todo4j.controller;

import io.kamaal.todo4j.model.User;
import io.kamaal.todo4j.service.UserService;
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
}
