package com.psu.Lionchat.service.chat;

public class ClassifierResponse {
	private String intent;

	public ClassifierResponse(String intent) {
		super();
		this.intent = intent;
	}

	public String getIntent() {
		return intent;
	}

}
