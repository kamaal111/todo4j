package io.kamaal.todo4j.user.service;

import io.kamaal.todo4j.user.exception.UserBadPayloadException;
import io.kamaal.todo4j.user.exception.UserNotFoundExceptionException;
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

    public User create(String username, String password) throws UserBadPayloadException {
        User user;
        try {
            user = new User(username, password);
        } catch (NullPointerException e) {
            throw new UserBadPayloadException();
        }

        return repo.save(user);
    }

    public User findByUsername(String username) throws UserNotFoundExceptionException {
        return repo
                .findByUsername(username)
                .orElseThrow(UserNotFoundExceptionException::new);
    }
}
