package com.psu.Lionchat.service.chat.responses;

import java.util.List;

public class SimilarityResponse {
	List<String> titles;
	List<String> urls;

	public SimilarityResponse(List<String> titles, List<String> urls) {
		super();
		this.titles = titles;
		this.urls = urls;
	}

	public List<String> getTitles() {
		return titles;
	}

	public List<String> getUrls() {
		return urls;
	}

	@Override
	public String toString() {
		return "SimilarityResponse [titles=" + titles + ", urls=" + urls + "]";
	}
}
