package com.gjm.webquizengine.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;
    private User malformedUser;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("gjm");
        user.setPassword("123456");
        user.setEmail("gjm@gmail.com");

        malformedUser = new User();
        malformedUser.setUsername("gjm");
        malformedUser.setPassword("123456");
        malformedUser.setEmail("fake email");
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
    void login() {

    }
}