package com.psu.Lionchat.dao.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Review {
	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	@JoinColumn
	private User user;
	
	private int score;

	@SuppressWarnings("unused")
	private Review() {
		
	}
	
	public Review(User user, int score) {
		super();
		this.user = user;
		this.score = score;
	}

}
