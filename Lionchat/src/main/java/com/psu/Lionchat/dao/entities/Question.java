package com.psu.Lionchat.dao.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Question {
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	@JoinColumn
	private User user;
	
	private String inputString;
	
	private boolean answered;
	
	@SuppressWarnings("unused")
	private Question() {
		
	}
	
}
