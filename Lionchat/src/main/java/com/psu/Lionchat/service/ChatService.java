package com.psu.Lionchat.service;

import javax.servlet.http.HttpServletRequest;

public interface ChatService {
	public String getAnswer(HttpServletRequest request, String question);
	
	public void submitFeedback(HttpServletRequest request, boolean helpful);
	
	public void submitReview(HttpServletRequest request, int rating);
}
