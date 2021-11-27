package com.psu.Lionchat.dao.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class InappropriateQuestion {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	
	@OneToOne(optional = false)
	@JoinColumn
	private Question question;
}
