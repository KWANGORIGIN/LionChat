package com.psu.Lionchat.service.chat;

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
