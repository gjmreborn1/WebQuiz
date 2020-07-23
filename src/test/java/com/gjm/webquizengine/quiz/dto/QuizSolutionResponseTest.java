package com.gjm.webquizengine.quiz.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuizSolutionResponseTest {
    @Test
    void getFeedback() {
        QuizSolutionResponse quizSolutionResponseSuccess = new QuizSolutionResponse(true);
        QuizSolutionResponse quizSolutionResponseFailure = new QuizSolutionResponse(false);

        assertEquals("Congratulations, you're right!", quizSolutionResponseSuccess.getFeedback());
        assertEquals("Wrong answer! Please, try again.", quizSolutionResponseFailure.getFeedback());
    }
}