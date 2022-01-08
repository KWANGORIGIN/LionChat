package com.psu.Lionchat.dao.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Intent implements Serializable {
	@Id
	@GeneratedValue
	private Long id;

	private String intent;

	public Intent(String intent) {
		super();
		this.intent = intent;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIntent() {
		return intent;
	}

	public void setIntent(String intent) {
		this.intent = intent;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, intent);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Intent other = (Intent) obj;
		return Objects.equals(id, other.id) && Objects.equals(intent, other.intent);
	}

	@Override
	public String toString() {
		return "Intent [id=" + id + ", intent=" + intent + "]";
	}

}
