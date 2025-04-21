package io.kamaal.todo4j.user.util;

import jakarta.validation.constraints.NotNull;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtils {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    private PasswordUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static String hashPassword(@NonNull @NotNull String rawPassword) {
        return encoder.encode(rawPassword);
    }

    public static boolean checkPassword(@NonNull @NotNull String rawPassword, @NonNull @NotNull String hashed) {
        return encoder.matches(rawPassword, hashed);
    }
}
