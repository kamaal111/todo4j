package io.kamaal.todo4j.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @NonNull
    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Setter
    @NonNull
    @Column(nullable = false)
    private String password;

    public User(@NonNull String username, @NonNull String password) {
        this.username = username;
        this.password = password;
    }
}
