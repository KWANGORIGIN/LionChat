package com.psu.Lionchat.dao.entities;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * An ORM mapping of the database's Question table.
 * 
 * @author jacobkarabin
 */
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

	public Question(User user, String inputString, boolean answered) {
		super();
		this.user = user;
		this.inputString = inputString;
		this.answered = answered;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getInputString() {
		return inputString;
	}

	public void setInputString(String inputString) {
		this.inputString = inputString;
	}

	public boolean isAnswered() {
		return answered;
	}

	public void setAnswered(boolean answered) {
		this.answered = answered;
	}

	@Override
	public int hashCode() {
		return Objects.hash(answered, id, inputString, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		return answered == other.answered && Objects.equals(id, other.id)
				&& Objects.equals(inputString, other.inputString) && Objects.equals(user, other.user);
	}

	@Override
	public String toString() {
		return "Question [id=" + id + ", user=" + user + ", inputString=" + inputString + ", answered=" + answered
				+ "]";
	}

}
