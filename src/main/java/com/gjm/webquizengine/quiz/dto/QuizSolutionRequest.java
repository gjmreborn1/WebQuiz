package com.gjm.webquizengine.quiz.dto;

import lombok.Data;

import java.util.List;

@Data
public class QuizSolutionRequest {
    private List<Integer> answer;
}
