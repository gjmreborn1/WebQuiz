package com.gjm.webquizengine.quiz;

import java.util.List;

public interface QuizService {
    Quiz findQuizById(int id);
    List<Quiz> findAllQuizzes();
    void addQuiz(Quiz quiz);
    boolean solveQuiz(int id, List<Integer> answer);
    void deleteQuiz(int id);
}
