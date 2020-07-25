package com.gjm.webquizengine.quiz.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuizSolutionResponseTest {
    @Test
    void getFeedbackSuccess() {
        QuizSolutionResponse responseSuccess = new QuizSolutionResponse(true);

        assertEquals("Congratulations, you're right!", responseSuccess.getFeedback());
    }

    @Test
    void getFeedbackFailure() {
        QuizSolutionResponse responseFailure = new QuizSolutionResponse(false);

        assertEquals("Wrong answer! Please, try again.", responseFailure.getFeedback());
    }
}