package com.example.Quiz_FP.Controller;

import com.example.Quiz_FP.model.Quiz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    @Autowired
    private QuizService quizService;


    @PostMapping("/create")
    public ResponseEntity<Quiz> createQuiz(@RequestBody Quiz quiz) {
        Quiz created = quizService.createQuizFromOpenTDB(quiz);
        return ResponseEntity.ok(created);
    }


    @GetMapping("/all")
    public ResponseEntity<List<Quiz>> getAllQuizzes() {
        return ResponseEntity.ok(quizService.getAllQuizzes());
    }


    @PutMapping("/{id}/update")
    public ResponseEntity<String> updateQuiz(@PathVariable Long id, @RequestBody Quiz quiz) {
        boolean result = quizService.updateQuiz(id, quiz);
        return result
                ? ResponseEntity.ok("Quiz updated successfully")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Quiz not found");
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteQuiz(@PathVariable Long id) {
        boolean result = quizService.deleteQuiz(id);
        return result
                ? ResponseEntity.ok("Quiz deleted successfully")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Quiz not found");
    }
}
