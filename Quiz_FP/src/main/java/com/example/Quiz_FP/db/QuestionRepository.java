package com.example.Quiz_FP.db;


import com.example.Quiz_FP.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}

