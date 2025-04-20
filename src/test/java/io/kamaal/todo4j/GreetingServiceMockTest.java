package io.kamaal.todo4j;

import io.kamaal.todo4j.controller.GreetingController;
import io.kamaal.todo4j.service.GreetingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GreetingController.class)
public class GreetingServiceMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GreetingService service;

    @Test
    void greetingShouldReturnMessageFromService() throws Exception {
        when(service.greet("World"))
                .thenReturn("Hello, Mock");

        mockMvc.perform(get("/greeting"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello, Mock")));
    }

}
