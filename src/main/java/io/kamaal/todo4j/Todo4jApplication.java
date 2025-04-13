package io.kamaal.todo4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Todo4jApplication {

    public static void main(String[] args) {
        SpringApplication.run(Todo4jApplication.class, args);
    }
}
