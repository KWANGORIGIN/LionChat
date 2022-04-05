package com.psu.Lionchat.service.chat;

import javax.servlet.http.HttpServletRequest;

import com.psu.Lionchat.service.chat.requests.FeedbackRequest;
import com.psu.Lionchat.service.chat.requests.ReviewPutRequest;
import com.psu.Lionchat.service.chat.responses.ChatAnswer;

public interface ChatService {
	public ChatAnswer getAnswer(HttpServletRequest request, String question);
	
	public void submitFeedback(HttpServletRequest request, FeedbackRequest feedbackRequest);
	
	public long submitReview(HttpServletRequest request, int score);
	
	public void updateReview(HttpServletRequest request, ReviewPutRequest reviewPutRequest);
}
