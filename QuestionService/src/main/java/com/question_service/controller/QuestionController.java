package com.question_service.controller;

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

import com.question_service.model.Question;
import com.question_service.model.QuestionWrapper;
import com.question_service.model.Response;
import com.question_service.service.QuestionService;

@RestController
@RequestMapping("question")
public class QuestionController {

	@Autowired
	QuestionService service;

	@GetMapping("allQuestions")
	public ResponseEntity<List<Question>> allQuestions() {
		try {
			return new ResponseEntity<List<Question>>(service.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<Question>>(new ArrayList<Question>(), HttpStatus.EXPECTATION_FAILED);
	}

	@GetMapping("category/{category}")
	public ResponseEntity<List<Question>> questionsByCategory(@PathVariable String category) {
		try {
			return new ResponseEntity<List<Question>>(service.findByCategory(category), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<Question>>(new ArrayList<Question>(), HttpStatus.BAD_REQUEST);
	}

	@PostMapping("add")
	public ResponseEntity<Question> addQuestion(@RequestBody Question question) {
		try {
			return new ResponseEntity<Question>(service.save(question), HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Question>(new Question(), HttpStatus.EXPECTATION_FAILED);

	}

	@GetMapping("generate")
	public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String category,
			@RequestParam Integer numQuestions) {
		try {
			return new ResponseEntity<List<Integer>>(service.findRandomQuestionsByCategory(category, numQuestions),
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<Integer>>(new ArrayList<Integer>(), HttpStatus.BAD_REQUEST);
	}

	@PostMapping("getQuestions")
	public ResponseEntity<List<QuestionWrapper>> getQuestionsById(@RequestBody List<Integer> questionIds) {
		try {

			List<QuestionWrapper> wrappers = new ArrayList<QuestionWrapper>();
			for (Integer id : questionIds) {
				Question question = service.findById(id).get();
				QuestionWrapper qw = new QuestionWrapper(question.getId(), question.getQuestionTitle(),
						question.getOption1(), question.getOption2(), question.getOption3(), question.getOption4());
				wrappers.add(qw);
			}

			return new ResponseEntity<List<QuestionWrapper>>(wrappers, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<QuestionWrapper>>(new ArrayList<QuestionWrapper>(), HttpStatus.BAD_REQUEST);
	}

	@PostMapping("getScore")
	public ResponseEntity<Integer> calculateScore(@RequestBody List<Response> responses) {
		try {
			int score = 0;
			List<QuestionWrapper> wrappers = new ArrayList<QuestionWrapper>();
			for (Response response : responses) {
				Question question = service.findById(response.getId()).get();
				if (response.getResponse().equals(question.getRightAnswer()))
					score++;
			}

			return new ResponseEntity<Integer>(score, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Integer>(0, HttpStatus.BAD_REQUEST);
	}

}
