package com.gjm.webquizengine.quiz;

import com.gjm.webquizengine.quiz.error_handling.NoQuizException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuizServiceDatabaseImplTest {
    @Mock
    private QuizRepository quizRepository;

    private QuizServiceDatabaseImpl quizService;
    private List<Quiz> quizzes;

    @BeforeEach
    void setUp() {
        quizzes = List.of(new Quiz("title1", "text1", Collections.emptyList(), List.of(0, 1)),
                          new Quiz("title2", "text2", Collections.emptyList(), null));
        quizzes.get(0).setId(5);

        quizService = new QuizServiceDatabaseImpl(quizRepository);
    }

    @Test
    void findAllQuizzes() {
        when(quizRepository.findAll()).thenReturn(quizzes);
        assertEquals(quizzes, quizService.findAllQuizzes());
    }

    @Test
    void addQuiz() {
        Quiz tempQuiz = new Quiz();
        quizService.addQuiz(tempQuiz);

        verify(quizRepository, times(1)).save(tempQuiz);
    }

    @Test
    void deleteQuiz() {
        when(quizRepository.findAll()).thenReturn(quizzes);
        quizService.deleteQuiz(5);

        verify(quizRepository, times(1)).deleteById(5);
    }

    @Test
    void findQuizById() {
        when(quizRepository.findAll()).thenReturn(quizzes);

        assertEquals(quizzes.get(0), quizService.findQuizById(5));
        assertThrows(NoQuizException.class, () -> quizService.findQuizById(1));
    }

    @Test
    void solveQuiz() {
        when(quizRepository.findAll()).thenReturn(quizzes);

        assertTrue(quizService.solveQuiz(5, List.of(0, 1)));
    }
}