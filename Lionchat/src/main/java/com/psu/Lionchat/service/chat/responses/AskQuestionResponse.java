package com.psu.Lionchat.service.chat.responses;

public class AskQuestionResponse {
	private ChatAnswer answer;
	private boolean shouldReview;

	public AskQuestionResponse(ChatAnswer answer, boolean shouldReview) {
		super();
		this.answer = answer;
		this.shouldReview = shouldReview;
	}

	public ChatAnswer getAnswer() {
		return answer;
	}

	public boolean isShouldReview() {
		return shouldReview;
	}

}
