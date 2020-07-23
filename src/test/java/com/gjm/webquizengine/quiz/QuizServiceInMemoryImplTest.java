package com.gjm.webquizengine.quiz;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class QuizServiceInMemoryImplTest {
    private QuizServiceInMemoryImpl quizService;
    private Quiz quiz;

    @BeforeEach
    void setUp() {
        quizService = new QuizServiceInMemoryImpl();

        quiz = new Quiz("title", "text", Collections.emptyList(), Collections.emptyList());
        quiz.setId(5);
    }

    @Test
    void findAllQuizzes() {
        assertEquals(Collections.emptyList(), quizService.findAllQuizzes());
    }

    @Test
    void addQuiz() {
        quizService.addQuiz(quiz);

        assertTrue(quizService.findAllQuizzes().contains(quiz));
    }

    @Test
    void deleteQuiz() {
        quizService.addQuiz(quiz);
        quizService.deleteQuiz(5);

        assertFalse(quizService.findAllQuizzes().contains(quiz));
    }
}