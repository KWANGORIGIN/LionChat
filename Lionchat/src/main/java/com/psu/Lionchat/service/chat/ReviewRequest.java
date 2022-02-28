package com.psu.Lionchat.service.chat;

public class ReviewRequest {
	private long questionId;
	private int review;

	public ReviewRequest(long questionId, int review) {
		super();
		this.questionId = questionId;
		this.review = review;
	}

	public long getQuestionId() {
		return questionId;
	}

	public int getReview() {
		return review;
	}

	@Override
	public String toString() {
		return "ReviewRequest [questionId=" + questionId + ", review=" + review + "]";
	}
}
