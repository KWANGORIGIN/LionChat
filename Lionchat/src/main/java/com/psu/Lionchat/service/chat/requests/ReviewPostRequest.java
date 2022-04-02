package com.psu.Lionchat.service.chat.requests;

public class ReviewPostRequest {
	private int review;

	public ReviewPostRequest(int review) {
		super();
		this.review = review;
	}

	public int getReview() {
		return review;
	}
	
	public void setReview(int review) {
		this.review = review;
	}

	@Override
	public String toString() {
		return "ReviewPostRequest [review=" + review + "]";
	}
}
