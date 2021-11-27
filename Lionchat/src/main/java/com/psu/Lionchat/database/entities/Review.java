package com.psu.Lionchat.database.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Review {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

//	@ManyToOne
//	@JoinColumn(name = "")
//	@Column(name = "session")
//	private Session session;

	@Column(name = "score")
	private int score;

	@SuppressWarnings("unused")
	private Review() {
		
	}
	
	public Review(int score) {
		super();
		this.score = score;
	}

}
