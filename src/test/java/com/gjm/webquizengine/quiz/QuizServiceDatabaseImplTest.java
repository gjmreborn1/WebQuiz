package com.gjm.webquizengine.quiz;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuizServiceDatabaseImplTest {
    @Spy
    private QuizRepository quizRepository;

    private QuizServiceDatabaseImpl quizService;
    private List<Quiz> quizzes;

    @BeforeEach
    void setUp() {
        quizzes = List.of(new Quiz("title1", "text1", Collections.emptyList(), null), new Quiz("title2", "text2", Collections.emptyList(), null));
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
}