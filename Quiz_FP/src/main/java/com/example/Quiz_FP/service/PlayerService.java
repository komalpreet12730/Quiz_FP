package com.example.Quiz_FP.service;


import com.example.Quiz_FP.db.QuestionRepository;
import com.example.Quiz_FP.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PlayerService {

    @Autowired
    private QuizRepository quizRepo;

    @Autowired
    private QuestionRepository questionRepo;

    public List<Question> getQuizQuestions(Long quizId) {
        Optional<Quiz> quiz = quizRepo.findById(quizId);
        return quiz.map(Quiz::getQuestions).orElse(Collections.emptyList());
    }

    public int calculateScore(Long quizId, List<String> submittedAnswers) {
        Optional<Quiz> quiz = quizRepo.findById(quizId);
        if (quiz.isEmpty()) return 0;

        List<Question> questions = quiz.get().getQuestions();
        int score = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (i < submittedAnswers.size() && questions.get(i).getCorrectAnswer().equalsIgnoreCase(submittedAnswers.get(i))) {
                score++;
            }
        }
        return score;
    }

    public void likeQuiz(Long quizId) {
        quizRepo.findById(quizId).ifPresent(quiz -> {
            quiz.setLikes(quiz.getLikes() + 1);
            quizRepo.save(quiz);
        });
    }

    public void unlikeQuiz(Long quizId) {
        quizRepo.findById(quizId).ifPresent(quiz -> {
            quiz.setLikes(Math.max(0, quiz.getLikes() - 1));
            quizRepo.save(quiz);
        });
    }
}
