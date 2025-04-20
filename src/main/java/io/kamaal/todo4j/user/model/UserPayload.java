package io.kamaal.todo4j.user.model;

import java.util.Optional;

public record UserPayload(Optional<String> username, Optional<String> password) {  }
