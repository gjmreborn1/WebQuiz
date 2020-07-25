package com.gjm.webquizengine.quiz;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Qualifier("InMemoryQuizService")
@RequiredArgsConstructor
public class QuizServiceInMemoryImpl implements QuizService {
    private static int counter = 0;
    private final List<Quiz> quizzes;

    public QuizServiceInMemoryImpl() {
        quizzes = new ArrayList<>();
    }

    @Override
    public Page<Quiz> findAllQuizzesPaged(int page) {
        PageRequest pageable = PageRequest.of(page, 10);
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), quizzes.size());

        return new PageImpl<>(quizzes.subList(start, end), pageable, quizzes.size());
    }

    @Override
    public List<Quiz> findAllQuizzes() {
        return quizzes;
    }

    @Override
    public void addQuiz(Quiz quiz) {
        counter++;
        quiz.setId(counter);

        quizzes.add(quiz);
    }

    @Override
    public void deleteQuiz(int id) {
        findQuizById(id);

        quizzes.removeIf(quiz -> quiz.getId() == id);
    }
}
