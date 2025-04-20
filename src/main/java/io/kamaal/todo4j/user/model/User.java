package io.kamaal.todo4j.user.model;

import io.kamaal.todo4j.user.util.PasswordUtils;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false)
    private String password;

    // Protected no-args constructor required by JPA
    protected User() { }

    public User(String username, String password) throws NullPointerException {
        this.username = checkUsername(username);
        this.password = encodePassword(checkPassword(password));
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
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
