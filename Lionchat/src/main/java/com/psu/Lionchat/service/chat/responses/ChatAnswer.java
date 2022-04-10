package com.psu.Lionchat.service.chat.responses;

public class ChatAnswer {
	private long questionId;
	private String answer;
	private String intent;

	public ChatAnswer() {
		super();
	}

	public ChatAnswer(long questionId, String answer) {
		super();
		this.questionId = questionId;
		this.answer = answer;
		this.intent = "null";
	}

	public ChatAnswer(long questionId, String answer, String intent) {
		super();
		this.questionId = questionId;
		this.answer = answer;
		this.intent = intent;
	}

	public long getQuestionId() {
		return questionId;
	}

	public String getAnswer() {
		return answer;
	}

	public String getIntent() {
		return intent;
	}

}
