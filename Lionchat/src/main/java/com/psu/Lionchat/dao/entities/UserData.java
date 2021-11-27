package com.psu.Lionchat.dao.entities;

import java.net.InetAddress;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class UserData {
	@Id
	@GeneratedValue
	private Long id;
	
	private InetAddress ip;
}
