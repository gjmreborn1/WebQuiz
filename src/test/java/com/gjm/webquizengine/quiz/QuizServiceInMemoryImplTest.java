package com.gjm.webquizengine.quiz;

import com.gjm.webquizengine.quiz.error_handling.NoQuizException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuizServiceInMemoryImplTest {
    private QuizServiceInMemoryImpl quizService;
    private Quiz quiz;

    @BeforeEach
    void setUp() {
        quizService = new QuizServiceInMemoryImpl();

        quiz = new Quiz("title", "text", Collections.emptyList(), List.of(0, 1));
        quiz.setId(5);
        quizService.addQuiz(quiz);
    }

    @Test
    void findAllQuizzes() {
        assertEquals(List.of(quiz), quizService.findAllQuizzes());
    }

    @Test
    void addQuiz() {
        assertTrue(quizService.findAllQuizzes().contains(quiz));
    }

    @Test
    void deleteQuiz() {
        quizService.deleteQuiz(5);

        assertFalse(quizService.findAllQuizzes().contains(quiz));
    }

    @Test
    void findQuizById() {
        assertEquals(quiz, quizService.findQuizById(5));
        assertThrows(NoQuizException.class, () -> quizService.findQuizById(1));
    }

    @Test
    void solveQuiz() {
        assertTrue(quizService.solveQuiz(5, List.of(0, 1)));
    }
}