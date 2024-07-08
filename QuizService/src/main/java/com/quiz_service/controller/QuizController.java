package com.quiz_service.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quiz_service.feign.QuizInterface;
import com.quiz_service.model.QuestionWrapper;
import com.quiz_service.model.Quiz;
import com.quiz_service.model.QuizDto;
import com.quiz_service.model.Response;
import com.quiz_service.service.QuizService;

@RestController
@RequestMapping("quiz")
public class QuizController {

	@Autowired
	QuizService quiz_service;
	
	@Autowired
	QuizInterface quizInterface;


	@PostMapping("create")
	public ResponseEntity<Quiz> createQuiz(@RequestBody QuizDto dto) {

		try {
			List<Integer> questionIds = quizInterface.getQuestionsForQuiz(dto.getCategory(), dto.getNumQ()).getBody();
			Quiz quiz = new Quiz();
			quiz.setQuestionIds(questionIds);
			quiz.setTitle(dto.getTitle());

			return new ResponseEntity<Quiz>(quiz_service.save(quiz), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Quiz>(new Quiz(), HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping("get/{id}")
	public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable int id) {

		try {
			List<Integer> questionIds = quiz_service.findById(id).get().getQuestionIds();

			List<QuestionWrapper> qw = quizInterface.getQuestionsById(questionIds).getBody();

			return new ResponseEntity<List<QuestionWrapper>>(qw, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<QuestionWrapper>>(new ArrayList(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("submit/{id}")
	public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses) {
		try {
			Integer score = quizInterface.calculateScore(responses).getBody();
			
			return new ResponseEntity<Integer>(score, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Integer>(0, HttpStatus.BAD_REQUEST);
		}
	}

}
