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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
    private String userJwt;

    private Quiz quiz;

    @BeforeEach
    void setUp() {
        quiz = new Quiz("title", "text", List.of("option1", "option2", "option3"), List.of(0, 1));

        userService.register(new User("gjm", "123456", "gjm@gmail.com"));
        userJwt = userService.login(new LoginDto("gjm", "123456"));
    }

    @Test
    void getQuizUnauthorized() throws Exception {
        mockMvc.perform(get("/api/quizzes/1"))
                .andExpect(status().is(401));
    }

    @Test
    void getQuizSuccess() throws Exception {
        quizService.addQuiz(quiz);

        mockMvc.perform(get("/api/quizzes/" + quiz.getId())
                .header("Authorization", userJwt))
                .andExpect(content().json(objectMapper.writeValueAsString(quiz)))
                .andExpect(status().is(200));
    }

    @Test
    void getQuizzesUnauthorized() throws Exception {
        mockMvc.perform(get("/api/quizzes"))
                .andExpect(status().is(401));
    }

    @Test
    void getQuizzesSuccess() throws Exception {
        int pageNumber = 0;
        List<Quiz> quizzes = List.of(quiz);
        quizService.addQuiz(quiz);

        mockMvc.perform(get("/api/quizzes?page=" + pageNumber)
                .header("Authorization", userJwt))
                .andExpect(content().json(objectMapper.writeValueAsString(
                        new PageImpl<>(quizzes, PageRequest.of(pageNumber, 10), quizzes.size())
                )))
                .andExpect(status().is(200));
    }

    @Test
    void addQuizUnauthorized() throws Exception {
        mockMvc.perform(post("/api/quizzes")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(quiz)))
                .andExpect(status().is(401));
    }

    @Test
    void addQuizMalformedData() throws Exception {
        Quiz malformedQuiz = new Quiz("", "", List.of("option1"), null);

        mockMvc.perform(post("/api/quizzes")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(malformedQuiz)))
                .andExpect(status().is(400));
    }

    @Test
    void addQuizSuccess() throws Exception {
        String jsonResponse = mockMvc.perform(post("/api/quizzes")
                .header("Authorization", userJwt)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(quiz)))
                .andExpect(status().is(200))
                .andReturn().getResponse().getContentAsString();

        assertEquals(2, objectMapper.readValue(jsonResponse, Quiz.class).getId());
    }

    @Test
    void solveQuizUnauthorized() throws Exception {
        mockMvc.perform(post("/api/quizzes/1/solve")
                .contentType("application/json")
                .content("{\"answer\": [0, 1]}"))
                .andExpect(status().is(401));
    }

    @Test
    void solveQuizMalformedData() throws Exception {
        mockMvc.perform(post("/api/quizzes/1/solve")
                .header("Authorization", userJwt)
                .contentType("application/json")
                .content("{}"))
                .andExpect(status().is(400));
    }

    @Test
    void solveQuizNoExisting() throws Exception {
        mockMvc.perform(post("/api/quizzes/-1/solve")
                .header("Authorization", userJwt)
                .contentType("application/json")
                .content("{\"answer\": [0, 2]}"))
                .andExpect(status().is(404));
    }

    @Test
    void solveQuizCorrect() throws Exception {
        quizService.addQuiz(quiz);

        String jsonResponse = mockMvc.perform(post("/api/quizzes/" + quiz.getId() + "/solve")
                .header("Authorization", userJwt)
                .contentType("application/json")
                .content("{\"answer\": [0, 1]}"))
                .andExpect(status().is(200))
                .andReturn().getResponse().getContentAsString();

        assertTrue(objectMapper.readValue(jsonResponse, QuizSolutionResponse.class).isSuccess());
    }

    @Test
    void solveQuizIncorrect() throws Exception {
        quizService.addQuiz(quiz);

        String jsonResponse = mockMvc.perform(post("/api/quizzes/" + quiz.getId() + "/solve")
                .header("Authorization", userJwt)
                .contentType("application/json")
                .content("{\"answer\": [0, 2]}"))
                .andExpect(status().is(200))
                .andReturn().getResponse().getContentAsString();

        assertFalse(objectMapper.readValue(jsonResponse, QuizSolutionResponse.class).isSuccess());
    }

    @Test
    void deleteQuizUnauthorized() throws Exception {
        mockMvc.perform(delete("/api/quizzes/1"))
                .andExpect(status().is(401));
    }

    @Test
    void deleteQuizNoExisting() throws Exception {
        mockMvc.perform(delete("/api/quizzes/-1")
                .header("Authorization", userJwt))
                .andExpect(status().is(404));
    }

    @Test
    void deleteQuizSuccess() throws Exception {
        quizService.addQuiz(quiz);

        mockMvc.perform(delete("/api/quizzes/" + quiz.getId())
                .header("Authorization", userJwt))
                .andExpect(status().is(204));
    }
}