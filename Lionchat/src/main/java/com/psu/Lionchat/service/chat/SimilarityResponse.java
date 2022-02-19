package com.psu.Lionchat.service.chat;

import java.util.List;

public class SimilarityResponse {
	List<String> searchresults;

	public SimilarityResponse(List<String> responses) {
		super();
		this.searchresults = responses;
	}

	public List<String> getResponses() {
		return searchresults;
	}

	@Override
	public String toString() {
		return "SimilarityResponse [searchresults=" + searchresults + "]";
	}

}
