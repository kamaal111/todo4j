package io.kamaal.todo4j;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "users")
public class User {
    @Id
    private Long id;

    private final String name;

    public User(String name) {
        this.name = name;
    }
}
