package com.psu.Lionchat.controllers;

import java.util.ArrayList;
import java.util.List;

public class InappropriateQueriesResponse {
	List<InappropriateData> inappropriateData;

	public InappropriateQueriesResponse() {
		super();
		this.inappropriateData = new ArrayList<>();
	}

	public void addData(Long key, String userIp, String question) {
		inappropriateData.add(new InappropriateData(key, userIp, question));
	}

	public List<InappropriateData> getInappropriateData() {
		return inappropriateData;
	}
}

class InappropriateData {
	private Long key;
	private String userIp;
	private String question;

	public InappropriateData(Long key, String userIp, String question) {
		super();
		this.key = key;
		this.userIp = userIp;
		this.question = question;
	}

	public Long getKey() {
		return key;
	}

	public String getUserIp() {
		return userIp;
	}

	public String getQuestion() {
		return question;
	}
}
