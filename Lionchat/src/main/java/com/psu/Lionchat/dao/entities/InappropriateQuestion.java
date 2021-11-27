package com.psu.Lionchat.dao.entities;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * An ORM mapping of the database's Inappropriate Question table.
 * 
 * @author jacobkarabin
 */
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

	public InappropriateQuestion(Long id, Question question) {
		super();
		this.id = id;
		this.question = question;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, question);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InappropriateQuestion other = (InappropriateQuestion) obj;
		return Objects.equals(id, other.id) && Objects.equals(question, other.question);
	}

	@Override
	public String toString() {
		return "InappropriateQuestion [id=" + id + ", question=" + question + "]";
	}

}
