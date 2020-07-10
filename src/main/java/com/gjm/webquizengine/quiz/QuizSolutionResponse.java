package com.gjm.webquizengine.quiz;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuizSolutionResponse {
    private boolean success;
    private String feedback;
}
