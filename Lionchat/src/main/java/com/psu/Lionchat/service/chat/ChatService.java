package com.psu.Lionchat.service.chat;

import javax.servlet.http.HttpServletRequest;

public interface ChatService {
	public ChatAnswer getAnswer(HttpServletRequest request, String question);
	
	public void submitFeedback(HttpServletRequest request, FeedbackRequest feedbackRequest);
	
	public void submitReview(HttpServletRequest request, ReviewRequest reviewRequest);
}
