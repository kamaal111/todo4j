package io.kamaal.todo4j.service;

import io.kamaal.todo4j.model.User;
import io.kamaal.todo4j.repository.UserRepository;
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

    public User findByUsername(String username) throws RuntimeException {
        return repo
                .findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
