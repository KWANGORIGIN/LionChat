package com.psu.Lionchat.service.chat;

import javax.servlet.http.HttpServletRequest;

import com.psu.Lionchat.service.chat.requests.FeedbackRequest;
import com.psu.Lionchat.service.chat.requests.ReviewPostRequest;
import com.psu.Lionchat.service.chat.requests.ReviewPutRequest;
import com.psu.Lionchat.service.chat.responses.ChatAnswer;
import com.psu.Lionchat.service.chat.responses.ReviewResponse;

public interface ChatService {
	public ChatAnswer getAnswer(HttpServletRequest request, String question);
	
	public void submitFeedback(HttpServletRequest request, FeedbackRequest feedbackRequest);
	
	public ReviewResponse submitReview(HttpServletRequest request, ReviewPostRequest reviewPostRequest);
	
	public void updateReview(HttpServletRequest request, ReviewPutRequest reviewPutRequest);
}
