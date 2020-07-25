package com.gjm.webquizengine.quiz;

import com.gjm.webquizengine.quiz.error_handling.NoQuizException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static java.util.Collections.EMPTY_LIST;
import static org.junit.jupiter.api.Assertions.*;

class QuizServiceInMemoryImplTest {
    private QuizServiceInMemoryImpl quizService;
    private Quiz quiz;

    @BeforeEach
    void setUp() {
        quiz = new Quiz("title", "text", Collections.emptyList(), List.of(0, 1));
        quiz.setId(1);

        quizService = new QuizServiceInMemoryImpl();
    }

    @Test
    void findAllQuizzes() {
        quizService.findAllQuizzes();

        assertEquals(EMPTY_LIST, quizService.findAllQuizzes());
    }

    @Test
    void addQuiz() {
        quizService.addQuiz(quiz);

        assertEquals(List.of(quiz), quizService.findAllQuizzes());
    }

    @Test
    void deleteQuiz() {
        quizService.addQuiz(quiz);

        quizService.deleteQuiz(1);

        assertEquals(EMPTY_LIST, quizService.findAllQuizzes());
    }

    @Test
    void findQuizByIdExistent() {
        quizService.addQuiz(quiz);

        assertEquals(quiz, quizService.findQuizById(quiz.getId()));
    }

    @Test
    void findQuizByIdNoExistent() {
        assertThrows(NoQuizException.class, () -> quizService.findQuizById(-1));
    }

    @Test
    void solveQuizSuccess() {
        quizService.addQuiz(quiz);

        assertTrue(quizService.solveQuiz(1, List.of(0, 1)));
    }

    @Test
    void solveQuizFailure() {
        quizService.addQuiz(quiz);

        assertFalse(quizService.solveQuiz(1, List.of(1)));
    }
}