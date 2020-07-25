package com.gjm.webquizengine.quiz;

import com.gjm.webquizengine.JwtUtils;
import com.gjm.webquizengine.quiz.dto.QuizSolutionRequest;
import com.gjm.webquizengine.quiz.dto.QuizSolutionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api")
public class QuizController {
    private final QuizService quizService;
    private final JwtUtils jwtUtils;

    @Autowired
    public QuizController(@Qualifier("DatabaseQuizService") QuizService quizService,
                          JwtUtils jwtUtils) {
        this.quizService = quizService;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping(path = "/quizzes/{id}")
    public Quiz getQuiz(@PathVariable int id,
                        @RequestHeader(name = "Authorization", required = false) String token) {
        jwtUtils.validateToken(token);

        return quizService.findQuizById(id);
    }

    @GetMapping(path = "/quizzes")
    public Page<Quiz> getQuizzes(
            @RequestParam("page") int page,
            @RequestHeader(name = "Authorization", required = false) String token) {
        jwtUtils.validateToken(token);

        return quizService.findAllQuizzesPaged(page);
    }

    @PostMapping(path = "/quizzes")
    public Quiz addQuiz(@RequestBody @Valid Quiz quiz,
                        @RequestHeader(name = "Authorization", required = false) String token) {
        jwtUtils.validateToken(token);

        quizService.addQuiz(quiz);
        return quiz;
    }

    @PostMapping(path = "/quizzes/{id}/solve")
    public QuizSolutionResponse solveQuiz(@PathVariable int id,
                                          @RequestBody @Valid QuizSolutionRequest quizSolutionRequest,
                                          @RequestHeader(name = "Authorization", required = false) String token) {
        jwtUtils.validateToken(token);

        boolean solveResult = quizService.solveQuiz(id, quizSolutionRequest.getAnswer());
        return new QuizSolutionResponse(solveResult);
    }

    @DeleteMapping(path = "/quizzes/{id}")
    public ResponseEntity<?> deleteQuiz(@PathVariable int id,
                                     @RequestHeader(name = "Authorization", required = false) String token) {
        jwtUtils.validateToken(token);

        quizService.deleteQuiz(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
