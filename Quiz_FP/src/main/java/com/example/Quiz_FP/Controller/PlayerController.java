package com.example.Quiz_FP.Controller;

import com.example.Quiz_FP.model.Question;
import com.example.Quiz_FP.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/player")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @GetMapping("/quiz/{quizId}/questions")
    public ResponseEntity<List<Question>> getQuizQuestions(@PathVariable Long quizId) {
        List<Question> questions = playerService.getQuizQuestions(quizId);
        return ResponseEntity.ok(questions);
    }

    @PostMapping("/quiz/{quizId}/submit")
    public ResponseEntity<String> submitQuiz(@PathVariable Long quizId, @RequestBody List<String> answers) {
        int score = playerService.calculateScore(quizId, answers);
        return ResponseEntity.ok("You scored: " + score + "/10");
    }

    @PostMapping("/quiz/{quizId}/like")
    public ResponseEntity<String> likeQuiz(@PathVariable Long quizId) {
        playerService.likeQuiz(quizId);
        return ResponseEntity.ok("Liked quiz");
    }

    @PostMapping("/quiz/{quizId}/unlike")
    public ResponseEntity<String> unlikeQuiz(@PathVariable Long quizId) {
        playerService.unlikeQuiz(quizId);
        return ResponseEntity.ok("Unliked quiz");
    }
}

