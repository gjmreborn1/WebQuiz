package com.gjm.webquizengine.quiz;

import com.gjm.webquizengine.quiz.error_handling.NoQuizException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
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
        when(quizService.findAllQuizzes()).thenReturn(List.of(quiz));

        assertEquals(quiz, quizService.findQuizById(5));
        assertThrows(NoQuizException.class, () -> quizService.findQuizById(1));
    }

    @Test
    void solveQuiz() {
        when(quizService.findQuizById(5)).thenReturn(quiz);

        assertTrue(quizService.solveQuiz(5, List.of(0, 1)));
    }
}