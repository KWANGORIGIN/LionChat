package com.psu.Lionchat.dao.entities;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * An ORM mapping of the database's CrashReport table.
 * 
 * @author jacobkarabin
 */
@Entity
public class CrashReport {
	@Id
	@GeneratedValue
	private Long id;

	private String stackTrace;

	@SuppressWarnings("unused")
	private CrashReport() {

	}

	public CrashReport(String stackTrace) {
		super();
		this.stackTrace = stackTrace;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStackTrace() {
		return stackTrace;
	}

	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}

	@Override
	public int hashCode() {
		return Objects.hash(stackTrace, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CrashReport other = (CrashReport) obj;
		return Objects.equals(stackTrace, other.stackTrace) && Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "CrashReport [id=" + id + ", stackTrace=" + stackTrace.substring(0, Math.min(stackTrace.length(), 10)) + "]";
	}
	
	
	
	

}
