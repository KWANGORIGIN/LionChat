package com.psu.Lionchat.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

	@Autowired
	SessionRepository<? extends Session> repository;
	
	
	@GetMapping("/session")
	String all(HttpSession session, HttpServletRequest request) {
		return session.getId();
	}
}
