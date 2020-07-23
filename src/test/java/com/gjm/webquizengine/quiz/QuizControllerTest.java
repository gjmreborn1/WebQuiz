package com.gjm.webquizengine.quiz;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gjm.webquizengine.quiz.dto.QuizSolutionResponse;
import com.gjm.webquizengine.user.User;
import com.gjm.webquizengine.user.UserService;
import com.gjm.webquizengine.user.dto.LoginDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class QuizControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    @Qualifier("DatabaseQuizService")
    private QuizService quizService;

    @Autowired
    private UserService userService;

    private Quiz quizToAdd;
    private Quiz quizToAdd2;
    private Quiz malformedQuiz;
    private String userJwt;

    @BeforeEach
    void setUp() {
        quizToAdd = new Quiz("title", "text", List.of("option1", "option2", "option3"), List.of(0, 1));
        quizToAdd2 = new Quiz("title1", "text1", List.of("option11", "option22", "option33"), null);
        malformedQuiz = new Quiz("", "", List.of("option1"), null);

        userService.register(new User("gjm", "123456", "gjm@gmail.com"));
        userJwt = userService.login(new LoginDto("gjm", "123456"));
    }

    @Test
    void getQuiz() throws Exception {
        mockMvc.perform(get("/api/quizzes/1"))
                .andExpect(status().is(401));

        quizService.addQuiz(quizToAdd);
        mockMvc.perform(get("/api/quizzes/" + quizToAdd.getId())
                .header("Authorization", userJwt))
                .andExpect(content().json(objectMapper.writeValueAsString(quizToAdd)))
                .andExpect(status().is(200));
    }

    @Test
    void getQuizzes() throws Exception {
        mockMvc.perform(get("/api/quizzes"))
                .andExpect(status().is(401));

        quizService.addQuiz(quizToAdd);
        quizService.addQuiz(quizToAdd2);
        mockMvc.perform(get("/api/quizzes")
                .header("Authorization", userJwt))
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(quizToAdd, quizToAdd2))))
                .andExpect(status().is(200));
    }

    @Test
    void addQuiz() throws Exception {
        mockMvc.perform(post("/api/quizzes")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(quizToAdd)))
                .andExpect(status().is(401));

        mockMvc.perform(post("/api/quizzes")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(malformedQuiz)))
                .andExpect(status().is(400));

        String jsonResponse = mockMvc.perform(post("/api/quizzes")
                .header("Authorization", userJwt)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(quizToAdd)))
                .andExpect(status().is(200))
                .andReturn().getResponse().getContentAsString();
        assertEquals(2, objectMapper.readValue(jsonResponse, Quiz.class).getId());
    }

    @Test
    void solveQuiz() throws Exception {
        mockMvc.perform(post("/api/quizzes/1/solve")
                .contentType("application/json")
                .content("{\"answer\": [0, 1]}"))
                .andExpect(status().is(401));

        mockMvc.perform(post("/api/quizzes/1/solve")
                .header("Authorization", userJwt)
                .contentType("application/json")
                .content("{}"))
                .andExpect(status().is(400));

        quizService.addQuiz(quizToAdd);
        String jsonResponse = mockMvc.perform(post("/api/quizzes/" + quizToAdd.getId() + "/solve")
                .header("Authorization", userJwt)
                .contentType("application/json")
                .content("{\"answer\": [0, 1]}"))
                .andExpect(status().is(200))
                .andReturn().getResponse().getContentAsString();
        assertTrue(objectMapper.readValue(jsonResponse, QuizSolutionResponse.class).isSuccess());

        jsonResponse = mockMvc.perform(post("/api/quizzes/" + quizToAdd.getId() + "/solve")
                .header("Authorization", userJwt)
                .contentType("application/json")
                .content("{\"answer\": [0, 2]}"))
                .andExpect(status().is(200))
                .andReturn().getResponse().getContentAsString();
        assertFalse(objectMapper.readValue(jsonResponse, QuizSolutionResponse.class).isSuccess());

        mockMvc.perform(post("/api/quizzes/5/solve")
                .header("Authorization", userJwt)
                .contentType("application/json")
                .content("{\"answer\": [0, 2]}"))
                .andExpect(status().is(404));
    }

    @Test
    void deleteQuiz() throws Exception {
        mockMvc.perform(delete("/api/quizzes/1"))
                .andExpect(status().is(401));

        quizService.addQuiz(quizToAdd);
        mockMvc.perform(delete("/api/quizzes/" + quizToAdd.getId())
                .header("Authorization", userJwt))
                .andExpect(status().is(204));
    }
}