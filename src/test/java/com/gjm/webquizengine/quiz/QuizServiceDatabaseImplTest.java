package com.gjm.webquizengine.quiz;

import com.gjm.webquizengine.quiz.error_handling.NoQuizException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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

    @InjectMocks
    private QuizServiceDatabaseImpl quizService;

    private List<Quiz> quizzes;

    @BeforeEach
    void setUp() {
        quizzes = List.of(new Quiz("title1", "text1", Collections.emptyList(), List.of(0, 1)),
                          new Quiz("title2", "text2", Collections.emptyList(), null));
        for(int i = 0; i < quizzes.size(); i++) {
            quizzes.get(i).setId(i + 1);
        }
    }

    @Test
    void findAllQuizzes() {
        when(quizRepository.findAll()).thenReturn(quizzes);

        assertEquals(quizzes, quizService.findAllQuizzes());
    }

    @Test
    void addQuiz() {
        Quiz quiz = new Quiz();
        quizService.addQuiz(quiz);

        verify(quizRepository, times(1)).save(quiz);
    }

    @Test
    void deleteQuiz() {
        when(quizRepository.findAll()).thenReturn(quizzes);

        quizService.deleteQuiz(1);

        verify(quizRepository, times(1)).deleteById(1);
    }

    @Test
    void findQuizByIdExistent() {
        when(quizRepository.findAll()).thenReturn(quizzes);

        Quiz quiz = quizzes.get(0);

        assertEquals(quiz, quizService.findQuizById(quiz.getId()));
    }

    @Test
    void findQuizByIdNoExistent() {
        when(quizRepository.findAll()).thenReturn(quizzes);

        assertThrows(NoQuizException.class, () -> quizService.findQuizById(-1));
    }

    @Test
    void solveQuizSuccess() {
        when(quizRepository.findAll()).thenReturn(quizzes);

        assertTrue(quizService.solveQuiz(1, List.of(0, 1)));
    }

    @Test
    void solveQuizFailure() {
        when(quizRepository.findAll()).thenReturn(quizzes);

        assertFalse(quizService.solveQuiz(1, List.of(1)));
    }
}