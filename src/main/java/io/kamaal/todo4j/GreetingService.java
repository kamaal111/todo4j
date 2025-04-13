package io.kamaal.todo4j;

import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
public class GreetingService {
    public String greet(String name) {
        return MessageFormat.format("Hello, {0}!", name);
    }
}
