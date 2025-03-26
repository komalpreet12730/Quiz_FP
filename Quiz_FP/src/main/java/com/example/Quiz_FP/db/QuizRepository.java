package com.example.Quiz_FP.db;

import com.example.Quiz_FP.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
}
