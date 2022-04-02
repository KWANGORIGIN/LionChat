package com.psu.Lionchat.service.chat.responses;

public class ReviewResponse {
	private long reviewId;

	public ReviewResponse(long reviewId) {
		super();
		this.reviewId = reviewId;
	}

	public long getReviewId() {
		return reviewId;
	}
}
