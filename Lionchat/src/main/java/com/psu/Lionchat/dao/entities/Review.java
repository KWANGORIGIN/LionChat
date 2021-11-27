package com.psu.Lionchat.dao.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Review {
	@Id
	@GeneratedValue
	private Long id;

//	@ManyToOne
//	@JoinColumn(name = "")
//	private Session session;

	private int score;

	@SuppressWarnings("unused")
	private Review() {
		
	}
	
	public Review(int score) {
		super();
		this.score = score;
	}

}
