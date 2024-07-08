package com.question_service.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.question_service.model.Question;
import com.question_service.model.QuestionWrapper;

@Service
public interface QuestionService extends JpaRepository<Question, Integer>{

	List<Question> findByCategory(String category);
	
	@Query(value = "SELECT q.id FROM question q Where q.category=:category ORDER BY RAND() LIMIT :numQ", nativeQuery = true)
    List<Integer> findRandomQuestionsByCategory(String category, int numQ);

}
