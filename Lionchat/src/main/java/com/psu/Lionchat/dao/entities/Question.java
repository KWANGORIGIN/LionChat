package com.psu.Lionchat.dao.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Question {
	@Id
	@GeneratedValue
	private Long id;
	
//	@Column(name = "session")
//	private  Session session;
	
	private String inputString;
	
	@SuppressWarnings("unused")
	private Question() {
		
	}
	
}
