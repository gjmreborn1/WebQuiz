package com.gjm.webquizengine.quiz;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuizTest {
    @Test
    void isCorrectNoAnswer() {
        Quiz quiz = new Quiz("title", "text", List.of("option1", "option2", "option3"), null);

        assertTrue(quiz.isCorrect(Collections.emptyList()));

        assertFalse(quiz.isCorrect(List.of(1, 2, 3)));
    }

    @Test
    void isCorrectOneAnswer() {
        Quiz quiz = new Quiz("title", "text", List.of("option1", "option2", "option3"), List.of(1));

        assertTrue(quiz.isCorrect(List.of(1)));

        assertFalse(quiz.isCorrect(List.of(2)));
        assertFalse(quiz.isCorrect(List.of(1, 2)));
    }

    @Test
    void isCorrectFewAnswer() {
        Quiz quiz = new Quiz("title", "text", List.of("option1", "option2", "option3"), List.of(1, 2));

        assertTrue(quiz.isCorrect(List.of(1, 2)));

        assertFalse(quiz.isCorrect(List.of(1, 2, 0)));
        assertFalse(quiz.isCorrect(List.of(1)));
        assertFalse(quiz.isCorrect(List.of(2)));
        assertFalse(quiz.isCorrect(Collections.emptyList()));
    }

    @Test
    void getAnswerDefaultConstructor() {
        Quiz quiz = new Quiz();

        assertNotNull(quiz.getAnswer());
    }

    @Test
    void getAnswerFromNull() {
        Quiz quiz = new Quiz("title", "text", new ArrayList<>(), null);

        assertNotNull(quiz.getAnswer());
    }
}