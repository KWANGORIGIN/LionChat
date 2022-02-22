package com.psu.Lionchat.service.chat;

public class Article {
	private String title;
	private String url;

	public Article(String title, String url) {
		super();
		this.title = title;
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public String getUrl() {
		return url;
	}

	@Override
	public String toString() {
		return "Article [title=" + title + ", url=" + url + "]";
	}
}
