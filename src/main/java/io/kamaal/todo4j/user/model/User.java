package io.kamaal.todo4j.user.model;

import io.kamaal.todo4j.user.util.PasswordUtils;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false)
    private String password;

    public User(String username, String password) throws NullPointerException {
        this.username = checkUsername(username);
        this.password = encodePassword(checkPassword(password));
    }

    private static String checkUsername(String username) throws NullPointerException {
        return Objects.requireNonNull(username, "Username can not be null");
    }

    private static String checkPassword(String password) throws NullPointerException {
        return Objects.requireNonNull(password, "Password can not be null");
    }

    private static String encodePassword(String password) {
        return PasswordUtils.hashPassword(password);
    }
}
