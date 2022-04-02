package com.psu.Lionchat.dao.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;

/**
 * An ORM mapping of the database's User table.
 * 
 * @author jacobkarabin
 */
@Entity
public class User implements Serializable {
	@Id
	@GeneratedValue
	private Long id;

	// ideally we could have Session as a @ManyToOne relationship
	// and join on it unfortunately this seems impossible?
	private String sessionId;

	private String ip;

	// mappedBy is the name of the field in Review/Question class that represents
	// this relationship. Forgetting to include this will cause JPA to create a
	// inefficient JOIN table. JOIN table should only be needed for many to many
	// relationship. Using mapped by will instead create a foreign key in
	// Review/Question table.
	@OneToMany(mappedBy = "user")
	private List<Review> reviews;

	@OneToMany(mappedBy = "user")
	private List<Question> questions;

	@SuppressWarnings("unused")
	public User() {

	}

	public User(String sessionId, String ip) {
		super();
		this.sessionId = sessionId;
		this.ip = ip;
		this.reviews = new ArrayList<>();
		this.questions = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, ip, sessionId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(id, other.id) && Objects.equals(ip, other.ip)
				&& Objects.equals(sessionId, other.sessionId);
	}

	// NEVER include Question/Review list in toString it will cause stackoverflow.
	@Override
	public String toString() {
		return "User [id=" + id + ", sessionId=" + sessionId + ", ip=" + ip + "]";
	}

}
