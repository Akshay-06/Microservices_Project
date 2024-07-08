package com.quiz_service.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.quiz_service.model.Quiz;

@Service
public interface QuizService extends JpaRepository<Quiz, Integer>{

}
