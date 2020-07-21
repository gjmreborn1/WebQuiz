package com.gjm.webquizengine.quiz;

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
    public List<Quiz> findAllQuizzes() {
        return quizRepository.findAll();
    }

    @Override
    public void addQuiz(Quiz quiz) {
        quizRepository.save(quiz);
    }

    @Override
    public void deleteQuiz(int id) {
        findQuizById(id);

        quizRepository.deleteById(id);
    }
}
