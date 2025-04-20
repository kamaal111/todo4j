package io.kamaal.todo4j.user.service;

import io.kamaal.todo4j.user.exception.UserNotFoundException;
import io.kamaal.todo4j.user.model.User;
import io.kamaal.todo4j.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class UserService {
    private final UserRepository repo;

    public User create(String username, String password) {
        var user = new User(username, password);

        return repo.save(user);
    }

    public User findByUsername(String username) throws UserNotFoundException {
        return repo
                .findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
    }
}
