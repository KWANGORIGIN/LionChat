package com.psu.Lionchat.dao.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class InappropriateQuestion {
	@Id
	@GeneratedValue
	private Long id;
	
	@OneToOne(optional = false)
	@JoinColumn
	private Question question;
	
	@SuppressWarnings("unused")
	private InappropriateQuestion() {
		
	}
}
