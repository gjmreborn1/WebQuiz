package com.gjm.webquizengine.quiz;

import com.gjm.webquizengine.quiz.error_handling.NoQuizException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier("DatabaseQuizService")
@AllArgsConstructor
public class QuizServiceDatabaseImpl implements QuizService {
    private final QuizRepository quizRepository;

    @Override
    public Quiz findQuizById(int id) {
        for(Quiz quiz : quizRepository.findAll()) {
            if(quiz.getId() == id) {
                return quiz;
            }
        }
        throw new NoQuizException();
    }

    @Override
    public List<Quiz> findAllQuizzes() {
        return quizRepository.findAll();
    }

    @Override
    public void addQuiz(Quiz quiz) {
        quizRepository.save(quiz);
    }

    @Override
    public boolean solveQuiz(int id, List<Integer> answer) {
        Quiz quiz = findQuizById(id);

        return quiz.isCorrect(answer);
    }
}
