package com.gjm.webquizengine.quiz;

import com.gjm.webquizengine.quiz.error_handling.NoQuizException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface QuizService {
    default Quiz findQuizById(int id) {
        return findAllQuizzes().stream()
                .filter(quiz -> quiz.getId() == id)
                .findFirst()
                .orElseThrow(NoQuizException::new);
    }

    default boolean solveQuiz(int id, List<Integer> answer) {
        Quiz quiz = findQuizById(id);

        return quiz.isCorrect(answer);
    }

    Page<Quiz> findAllQuizzesPaged(int page);
    List<Quiz> findAllQuizzes();
    void addQuiz(Quiz quiz);
    void deleteQuiz(int id);
}
