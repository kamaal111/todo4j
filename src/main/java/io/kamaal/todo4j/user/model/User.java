package io.kamaal.todo4j.user.model;

import io.kamaal.todo4j.user.util.PasswordUtils;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.NonNull;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @NotNull
    @Column(nullable = false)
    private String password;

    // Protected no-args constructor required by JPA
    protected User() { }

    public User(
            @NonNull @NotNull(message = "Username cannot be null") String username,
            @NonNull @NotNull(message = "Password cannot be null") String password
    ) {
        this.username = username;
        this.password = encodePassword(password);
    }

    public String getUsername() {
        return username;
    }

    private static String encodePassword(String password) {
        return PasswordUtils.hashPassword(password);
    }
}
