package com.psu.Lionchat.dao.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * An ORM mapping of the database's User table.
 * 
 * @author jacobkarabin
 */
@Entity
public class User implements Serializable{
	@Id
	@GeneratedValue
	private Long id;

	// ideally we could have Session as a @ManyToOne relationship
	// and join on it unfortunately this seems impossible?
	private String sessionId;

	private String ip;

	@SuppressWarnings("unused")
	public User() {

	}

	public User(String sessionId, String ip) {
		super();
		this.sessionId = sessionId;
		this.ip = ip;
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

	@Override
	public String toString() {
		return "User [id=" + id + ", sessionId=" + sessionId + ", ip=" + ip + "]";
	}

}
