package com.psu.Lionchat.service.chat;

public class SimilarityRequest {
	private String query;

	public SimilarityRequest(String query) {
		super();
		this.query = query;
	}

	public String getQuery() {
		return query;
	}
}
