package com.gjm.webquizengine.quiz.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class QuizSolutionRequest {
    @NotNull(message = "You must provide some answers")
    @NotEmpty(message = "You must provide some answers")
    private List<Integer> answer;
}
