package com.psu.Lionchat.service.chat.responses;

public class AskQuestionResponse {
	private ChatAnswer answer;
	private long reviewId;
	private boolean error;

	public AskQuestionResponse(ChatAnswer answer, long reviewId, boolean error) {
		super();
		this.answer = answer;
		this.reviewId = reviewId;
		this.error = error;
	}

	public ChatAnswer getAnswer() {
		return answer;
	}

	public long getReviewId() {
		return reviewId;
	}

	public boolean isError() {
		return error;
	}

}
