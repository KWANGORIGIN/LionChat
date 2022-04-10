package com.psu.Lionchat.service.chat.responses;

public class AskQuestionResponse {
	private ChatAnswer answer;
	private boolean shouldReview;
	private boolean error;

	public AskQuestionResponse(ChatAnswer answer, boolean shouldReview, boolean error) {
		super();
		this.answer = answer;
		this.shouldReview = shouldReview;
		this.error = error;
	}

	public ChatAnswer getAnswer() {
		return answer;
	}

	public boolean isShouldReview() {
		return shouldReview;
	}

	public boolean isError() {
		return error;
	}

}
