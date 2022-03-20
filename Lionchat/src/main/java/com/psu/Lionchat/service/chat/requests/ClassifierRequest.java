package com.psu.Lionchat.service.chat.requests;

public class ClassifierRequest {
	private String utterance;

	public ClassifierRequest(String question) {
		super();
		this.utterance = question;
	}

	public String getQuestion() {
		return utterance;
	}

}
