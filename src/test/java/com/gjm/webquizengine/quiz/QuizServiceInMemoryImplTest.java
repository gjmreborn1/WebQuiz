package com.gjm.webquizengine.quiz;

import com.gjm.webquizengine.quiz.error_handling.NoQuizException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

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

        quizService = new QuizServiceInMemoryImpl();
    }

    @Test
    void findAllQuizzes() {
        quizService.findAllQuizzes();

        assertEquals(EMPTY_LIST, quizService.findAllQuizzes());
    }

    @Test
    void findAllQuizzesPaged() {
        Page<Quiz> quizPage = new PageImpl<>(List.of(quiz));
        quizService.addQuiz(quiz);

        assertEquals(quizPage.getContent(), quizService.findAllQuizzesPaged(0).getContent());
    }

    @Test
    void addQuiz() {
        quizService.addQuiz(quiz);

        assertEquals(List.of(quiz), quizService.findAllQuizzes());
    }

    @Test
    void deleteQuiz() {
        quizService.addQuiz(quiz);

        quizService.deleteQuiz(quiz.getId());

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

        assertTrue(quizService.solveQuiz(quiz.getId(), List.of(0, 1)));
    }

    @Test
    void solveQuizFailure() {
        quizService.addQuiz(quiz);

        assertFalse(quizService.solveQuiz(quiz.getId(), List.of(1)));
    }
}