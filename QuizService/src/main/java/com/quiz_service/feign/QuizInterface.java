package com.quiz_service.feign;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.quiz_service.model.QuestionWrapper;
import com.quiz_service.model.Response;


@FeignClient("QUESTIONSERVICE")
public interface QuizInterface {

	
	@GetMapping("question/generate")
	public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String category,
			@RequestParam Integer numQuestions) ;

	@PostMapping("question/getQuestions")
	public ResponseEntity<List<QuestionWrapper>> getQuestionsById(@RequestBody List<Integer> questionIds) ;

	@PostMapping("question/getScore")
	public ResponseEntity<Integer> calculateScore(@RequestBody List<Response> responses) ;
	
}
