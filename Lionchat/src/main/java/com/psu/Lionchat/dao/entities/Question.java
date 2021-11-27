package com.psu.Lionchat.dao.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Question {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	
//	@Column(name = "session")
//	private  Session session;
	
	@Column(name = "input_string")
	private String inputString;
	
	@SuppressWarnings("unused")
	private Question() {
		
	}
	
}
