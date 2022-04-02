package com.psu.Lionchat.service.chat.requests;

public class ReviewPutRequest {
	private long reviewId;
	private int review;

	public ReviewPutRequest(long reviewId, int review) {
		super();
		this.reviewId = reviewId;
		this.review = review;
	}

	public long getReviewId() {
		return reviewId;
	}

	public int getReview() {
		return review;
	}

	@Override
	public String toString() {
		return "ReviewPutRequest [reviewId=" + reviewId + ", review=" + review + "]";
	}
}
