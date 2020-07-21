package com.gjm.webquizengine.quiz;

import com.gjm.webquizengine.quiz.error_handling.NoQuizException;

import java.util.List;

public interface QuizService {
    default Quiz findQuizById(int id) {
        for(Quiz quiz : findAllQuizzes()) {
            if(quiz.getId() == id) {
                return quiz;
            }
        }
        throw new NoQuizException();
    }

    default boolean solveQuiz(int id, List<Integer> answer) {
        Quiz quiz = findQuizById(id);

        return quiz.isCorrect(answer);
    }

    List<Quiz> findAllQuizzes();
    void addQuiz(Quiz quiz);
    void deleteQuiz(int id);
}
