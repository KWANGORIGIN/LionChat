package com.psu.Lionchat.controllers;

import java.util.ArrayList;
import java.util.List;

public class InappropriateQueriesResponse {
	List<InappropriateData> inappropriateData;

	public InappropriateQueriesResponse() {
		super();
		this.inappropriateData = new ArrayList<>();
	}
	
	public void addData(String userIp, String question) {
		inappropriateData.add(new InappropriateData(userIp, question));
	}

	public List<InappropriateData> getInappropriateData() {
		return inappropriateData;
	}
}

class InappropriateData {
	private String userIp;
	private String question;

	public InappropriateData(String userIp, String question) {
		super();
		this.userIp = userIp;
		this.question = question;
	}

	public String getUserIp() {
		return userIp;
	}

	public String getQuestion() {
		return question;
	}
}
