package com.gjm.webquizengine.quiz;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuizTest {
    @Test
    void isCorrect() {
        Quiz quizNoCorrectAnswer = new Quiz("title", "text", List.of("option1", "option2", "option3"), null);
        assertTrue(quizNoCorrectAnswer.isCorrect(Collections.emptyList()));
        assertFalse(quizNoCorrectAnswer.isCorrect(List.of(1, 2, 3)));

        Quiz quizOneCorrectAnswer = new Quiz("title", "text", List.of("option1", "option2", "option3"), List.of(0));
        assertTrue(quizOneCorrectAnswer.isCorrect(List.of(0)));
        assertFalse(quizOneCorrectAnswer.isCorrect(List.of(1)));
        assertFalse(quizOneCorrectAnswer.isCorrect(List.of(0, 1)));

        Quiz quizFewCorrectAnswer = new Quiz("title", "text", List.of("option1", "option2", "option3"), List.of(1, 2));
        assertTrue(quizFewCorrectAnswer.isCorrect(List.of(1, 2)));
        assertFalse(quizFewCorrectAnswer.isCorrect(List.of(1, 2, 0)));
        assertFalse(quizFewCorrectAnswer.isCorrect(List.of(1)));
        assertFalse(quizFewCorrectAnswer.isCorrect(List.of(2)));
        assertFalse(quizFewCorrectAnswer.isCorrect(Collections.emptyList()));
    }

    @Test
    void getAnswer() {
        Quiz quiz1 = new Quiz();
        Quiz quiz2 = new Quiz("title", "text", new ArrayList<>(), null);

        assertNotNull(quiz1.getAnswer());
        assertNotNull(quiz2.getAnswer());
    }
}