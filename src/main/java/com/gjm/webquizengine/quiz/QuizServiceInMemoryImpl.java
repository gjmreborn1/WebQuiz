package com.gjm.webquizengine.quiz;

import com.gjm.webquizengine.quiz.error_handling.NoQuizException;
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
    public Quiz findQuizById(int id) {
        for(Quiz quiz : quizzes) {
            if(quiz.getId() == id) {
                return quiz;
            }
        }
        throw new NoQuizException();
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
    public boolean solveQuiz(int id, List<Integer> answer) {
        Quiz quiz = findQuizById(id);

        return quiz.isCorrect(answer);
    }
}
