package com.gjm.webquizengine.quiz;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class QuizController {
    private List<Quiz> quizzes;

    public QuizController() {
        quizzes = new ArrayList<>();

        quizzes.add(new Quiz("The Java Logo", "What is depicted on the Java logo?", List.of("Robot", "Tea leaf", "Cup of coffee", "Bug")));
    }

    @GetMapping(path = "/quiz")
    public Quiz getQuiz() {
        return quizzes.get(0);
    }

    @PostMapping(path = "/quiz")
    public QuizSolutionResponse solveQuiz(@RequestBody String answer) {
        int value = Integer.parseInt(answer.split("=")[1]);

        if(value == 2) {
            return new QuizSolutionResponse(true, "Congratulations, you're right!");
        } else {
            return new QuizSolutionResponse(false, "Wrong answer! Please, try again.");
        }
    }
}
