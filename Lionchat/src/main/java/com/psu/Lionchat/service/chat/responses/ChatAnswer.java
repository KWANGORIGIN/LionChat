package com.psu.Lionchat.service.chat.responses;

public class ChatAnswer {
	private long questionId;
	private String answer;

	public ChatAnswer(long questionId, String answer) {
		super();
		this.questionId = questionId;
		this.answer = answer;
	}

	public long getQuestionId() {
		return questionId;
	}

	public String getAnswer() {
		return answer;
	}

}
