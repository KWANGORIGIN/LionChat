package com.psu.Lionchat.dao.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
	private User user;

	private int score;

	@CreationTimestamp
	private LocalDateTime creationTime;

	@UpdateTimestamp
	private LocalDateTime updateTime;

	@SuppressWarnings("unused")
	public Review() {

	}

	public Review(User user, int score) {
		super();
		this.user = user;
		this.score = score;
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

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public LocalDateTime getCreationTime() {
		return creationTime;
	}

	public LocalDateTime getUpdateTime() {
		return updateTime;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, score, user);
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
		return Objects.equals(id, other.id) && score == other.score && Objects.equals(user, other.user);
	}

	@Override
	public String toString() {
		return "Review [id=" + id + ", user=" + user + ", score=" + score + "]";
	}

}
