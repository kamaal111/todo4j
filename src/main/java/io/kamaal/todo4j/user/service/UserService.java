package io.kamaal.todo4j.user.service;

import io.kamaal.todo4j.user.exception.UserBadPayloadException;
import io.kamaal.todo4j.user.exception.UserNotFoundExceptionException;
import io.kamaal.todo4j.user.model.User;
import io.kamaal.todo4j.user.repository.UserRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public User create(
            @NonNull @NotNull(message = "Username cannot be null") String username,
            @NonNull @NotNull(message = "Password cannot be null") String password
    ) {
        var user = new User(username, password);

        return repo.save(user);
    }

    public User findByUsername(String username) throws UserNotFoundExceptionException {
        return repo
                .findByUsername(username)
                .orElseThrow(UserNotFoundExceptionException::new);
    }
}
