package io.kamaal.todo4j;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class GreetingControllerTest {

    @Autowired
    private GreetingController controller;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    void testGreeting() {
        var result = controller.greeting("Kamaal");

        assertThat(result.content()).isEqualTo("Hello, Kamaal!");
    }

}
