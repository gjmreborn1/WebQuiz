package com.gjm.webquizengine.quiz;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Qualifier("InMemoryQuizService")
public class QuizServiceInMemoryImpl implements QuizService {
    private final List<Quiz> quizzes;

    public QuizServiceInMemoryImpl() {
        quizzes = new ArrayList<>();
    }

    @Override
    public List<Quiz> findAllQuizzes() {
        return quizzes;
    }

    @Override
    public void addQuiz(Quiz quiz) {
        quizzes.add(quiz);
    }

    @Override
    public void deleteQuiz(int id) {
        findQuizById(id);

        quizzes.removeIf(quiz -> quiz.getId() == id);
    }
}
