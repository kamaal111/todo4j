package io.kamaal.todo4j.shared.model;

public record ErrorResponse(Long status, String message, String details) { }
