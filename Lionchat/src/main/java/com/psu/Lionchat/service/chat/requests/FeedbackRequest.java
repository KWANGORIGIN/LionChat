package com.psu.Lionchat.service.chat.requests;

public class FeedbackRequest {
	private long questionId;
	private boolean helpful;

	public FeedbackRequest(long questionId, boolean helpful) {
		super();
		this.questionId = questionId;
		this.helpful = helpful;
	}

	public long getQuestionId() {
		return questionId;
	}

	public boolean isHelpful() {
		return helpful;
	}

	@Override
	public String toString() {
		return "FeedbackRequest [questionId=" + questionId + ", helpful=" + helpful + "]";
	}
}
