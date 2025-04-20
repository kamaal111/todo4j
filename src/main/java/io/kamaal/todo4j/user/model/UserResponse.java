package io.kamaal.todo4j.user.model;

import jakarta.validation.constraints.NotNull;
import org.springframework.lang.NonNull;

public record UserResponse(@NonNull @NotNull String username) {
    public static UserResponse fromUser(User user) {
        return new UserResponse(user.getUsername());
    }
}
