package com.gjm.webquizengine.quiz;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api")
public class QuizController {
    private List<Quiz> quizzes;

    public QuizController() {
        quizzes = new ArrayList<>();
    }

    @GetMapping(path = "/quizzes/{id}")
    public ResponseEntity<Quiz> getQuiz(@PathVariable int id) {
        return findQuizById(id)
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/quizzes")
    public List<Quiz> getQuizzes() {
        return quizzes;
    }

    @PostMapping(path = "/quizzes")
    public Quiz addQuiz(@RequestBody Quiz quiz) {
        quizzes.add(quiz);

        return quiz;
    }

    @PostMapping(path = "/quizzes/{id}/solve")
    public ResponseEntity<QuizSolutionResponse> solveQuiz(@PathVariable int id,
                                                          @RequestBody String answer) {
        int value = Integer.parseInt(answer.split("=")[1]);
        Optional<Quiz> quiz = findQuizById(id);

        if(quiz.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if(value == quiz.get().getAnswer()) {
            return new ResponseEntity<>(
                    new QuizSolutionResponse(true, "Congratulations, you're right!"),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    new QuizSolutionResponse(false, "Wrong answer! Please, try again."),
                    HttpStatus.OK);
        }
    }

    private Optional<Quiz> findQuizById(int id) {
        for(Quiz quiz : quizzes) {
            if(quiz.getId() == id) {
                return Optional.of(quiz);
            }
        }
        return Optional.empty();
    }
}
