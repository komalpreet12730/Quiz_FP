package com.example.Quiz_FP.service;

import com.example.Quiz_FP.DTO.OTR;
import com.example.Quiz_FP.DTO.QuestionReq;
import com.example.Quiz_FP.model.Question;
import com.example.Quiz_FP.db.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepo;

    @Autowired
    private QuestionRepository questionRepo;

    private final RestTemplate restTemplate = new RestTemplate();

    // ✅ Create Quiz from OpenTDB API
    public Quiz createQuizFromOpenTDB(Quiz quizRequest) {
        // Save the quiz first
        Quiz savedQuiz = quizRepo.save(quizRequest);

        // OpenTDB API call
        String url = "https://opentdb.com/api.php?amount=10&difficulty=" +
                quizRequest.getDifficulty().toLowerCase() + "&type=multiple";

        OTR response = restTemplate.getForObject(url, OTR.class);

        List<Question> questionList = new ArrayList<>();

        if (response != null && response.getResults() != null) {
            for (QuestionReq q : response.getResults()) {
                Question question = new Question();
                question.setQuestion(q.getQuestion());
                question.setCorrectAnswer(q.getCorrect_answer());

                List<String> options = new ArrayList<>(q.getIncorrect_answers());
                options.add(q.getCorrect_answer());
                Collections.shuffle(options);

                question.setOption1(options.size() > 0 ? options.get(0) : "");
                question.setOption2(options.size() > 1 ? options.get(1) : "");
                question.setOption3(options.size() > 2 ? options.get(2) : "");

                question.setQuiz(savedQuiz);
                questionList.add(question);
            }

            // Save all questions together
            questionRepo.saveAll(questionList);
            savedQuiz.setQuestions(questionList); // ✅ Attach the questions to Quiz
        }

        return savedQuiz;
    }

    // ✅ Get all quizzes
    public List<Quiz> getAllQuizzes() {
        return quizRepo.findAll();
    }

    // ✅ Update quiz
    public boolean updateQuiz(Long id, Quiz quizData) {
        Optional<Quiz> optional = quizRepo.findById(id);
        if (optional.isPresent()) {
            Quiz existing = optional.get();
            existing.setName(quizData.getName());
            existing.setStartDate(quizData.getStartDate());
            existing.setEndDate(quizData.getEndDate());
            quizRepo.save(existing);
            return true;
        }
        return false;
    }

    // ✅ Delete quiz
    public boolean deleteQuiz(Long id) {
        Optional<Quiz> quiz = quizRepo.findById(id);
        if (quiz.isPresent()) {
            quizRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
