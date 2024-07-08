package com.quiz_service.model;

import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Quiz {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String title;

	@ElementCollection
	private List<Integer> questionIds;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	

	public List<Integer> getQuestionIds() {
		return questionIds;
	}

	public void setQuestionIds(List<Integer> questionIds) {
		this.questionIds = questionIds;
	}

	public Quiz(Integer id, String title, List<Integer> questionIds) {
		super();
		this.id = id;
		this.title = title;
		this.questionIds = questionIds;
	}

	public Quiz() {
		// TODO Auto-generated constructor stub
	}
	
	
}
