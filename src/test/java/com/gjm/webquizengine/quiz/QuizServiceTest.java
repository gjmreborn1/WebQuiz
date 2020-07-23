package com.gjm.webquizengine.quiz;

import com.gjm.webquizengine.quiz.error_handling.NoQuizException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuizServiceTest {
    @Spy
    private QuizService quizService;

    private Quiz quiz;

    @BeforeEach
    void setUp() {
        quiz = new Quiz("title", "text", List.of("option1", "option2", "option3"), List.of(0, 1));
        quiz.setId(5);
    }

    @Test
    void findQuizById() {
        try {
            when(quizService.findAllQuizzes()).thenReturn(List.of(quiz));

            assertEquals(quiz, quizService.findQuizById(5));
            assertThrows(NoQuizException.class, () -> quizService.findQuizById(1));
        } catch(Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Test
    void solveQuiz() {
        when(quizService.findAllQuizzes()).thenReturn(List.of(quiz));

        assertTrue(quizService.solveQuiz(5, List.of(0, 1)));
    }
}