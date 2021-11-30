package com.psu.Lionchat.dao.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * An ORM mapping of the database's Review table.
 * 
 * @author jacobkarabin
 */
@Entity
public class Review implements Serializable {
	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	@JoinColumn
	private Question question;

	private int score;

	@SuppressWarnings("unused")
	public Review() {

	}

	public Review(Question question, int score) {
		super();
		this.question = question;
		this.score = score;
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

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, score, question);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Review other = (Review) obj;
		return Objects.equals(id, other.id) && score == other.score && Objects.equals(question, other.question);
	}

	@Override
	public String toString() {
		return "Review [id=" + id + ", question=" + question + ", score=" + score + "]";
	}

}
