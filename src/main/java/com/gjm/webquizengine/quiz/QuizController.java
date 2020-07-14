package com.gjm.webquizengine.quiz;

import com.gjm.webquizengine.quiz.dto.QuizSolutionRequest;
import com.gjm.webquizengine.quiz.dto.QuizSolutionResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api")
@AllArgsConstructor
public class QuizController {
    @Qualifier("InMemoryQuizService")
    private final QuizService quizService;

    @GetMapping(path = "/quizzes/{id}")
    public Quiz getQuiz(@PathVariable int id) {
        return quizService.findQuizById(id);
    }

    @GetMapping(path = "/quizzes")
    public List<Quiz> getQuizzes() {
        return quizService.findAllQuizzes();
    }

    @PostMapping(path = "/quizzes")
    public Quiz addQuiz(@Valid @RequestBody Quiz quiz) {
        quizService.addQuiz(quiz);
        return quiz;
    }

    @PostMapping(path = "/quizzes/{id}/solve")
    public QuizSolutionResponse solveQuiz(@PathVariable int id, @RequestBody QuizSolutionRequest quizSolutionRequest) {
        boolean solveResult = quizService.solveQuiz(id, quizSolutionRequest.getAnswer());
        return new QuizSolutionResponse(solveResult);
    }
}
