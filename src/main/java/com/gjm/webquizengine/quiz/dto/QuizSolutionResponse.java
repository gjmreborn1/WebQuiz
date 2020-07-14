package com.gjm.webquizengine.quiz.dto;

import lombok.Data;

@Data
public class QuizSolutionResponse {
    private boolean success;
    private String feedback;

    public QuizSolutionResponse(boolean success) {
        this.success = success;

        if(success) {
            feedback = "Congratulations, you're right!";
        } else {
            feedback = "Wrong answer! Please, try again.";
        }
    }
}
