package io.kamaal.todo4j.user.model;

public record UserResponse(String username) {
    public static UserResponse fromUser(User user) {
        return new UserResponse(user.getUsername());
    }
}
