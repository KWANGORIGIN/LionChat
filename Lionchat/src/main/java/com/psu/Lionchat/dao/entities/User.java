package com.psu.Lionchat.dao.entities;

import java.net.InetAddress;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {
	@Id
	@GeneratedValue
	private Long id;

	// ideally we could have Session as a @ManyToOne relationship
	// and join on it unfortunately this seems impossible?
	private String sessionId;

	private InetAddress ip;
}
