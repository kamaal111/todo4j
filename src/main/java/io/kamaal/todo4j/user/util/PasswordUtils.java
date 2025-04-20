package io.kamaal.todo4j.user.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtils {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    private PasswordUtils() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static String hashPassword(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    public static boolean checkPassword(String rawPassword, String hashed) {
        return encoder.matches(rawPassword, hashed);
    }
}
