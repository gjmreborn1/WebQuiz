package com.gjm.webquizengine.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gjm.webquizengine.user.dto.LoginDto;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    private User user;
    private User malformedUser;
    private LoginDto loginDto;
    private LoginDto malformedLoginDto;

    @BeforeEach
    void setUp() {
        user = new User("gjm", "123456", "gjm@gmail.com");
        malformedUser = new User("gjm", "123456", "fake email");

        loginDto = new LoginDto("gjm", "123456");
        malformedLoginDto = new LoginDto("gjm", null);
    }

    @Test
    void register() throws Exception {
        mockMvc.perform(post("/api/register")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().is(200));

        mockMvc.perform(post("/api/register")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(malformedUser)))
                .andExpect(status().is(400));
    }

    @Test
    void login() throws Exception {
        userService.register(user);

        mockMvc.perform(post("/api/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().is(200))
                .andExpect(content().string(Matchers.any(String.class)));

        mockMvc.perform(post("/api/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(malformedLoginDto)))
                .andExpect(status().is(400));
    }
}