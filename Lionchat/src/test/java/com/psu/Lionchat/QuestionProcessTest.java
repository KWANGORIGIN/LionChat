
package com.psu.Lionchat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Base64;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.psu.Lionchat.dao.entities.Question;
import com.psu.Lionchat.dao.entities.Review;
import com.psu.Lionchat.dao.entities.User;
import com.psu.Lionchat.dao.repositories.QuestionRepository;
import com.psu.Lionchat.dao.repositories.ReviewRepository;
import com.psu.Lionchat.dao.repositories.UserRepository;
import com.psu.Lionchat.service.chat.requests.FeedbackRequest;
import com.psu.Lionchat.service.chat.requests.ReviewPostRequest;
import com.psu.Lionchat.service.chat.responses.ChatAnswer;
import com.psu.Lionchat.service.chat.responses.ReviewResponse;;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class QuestionProcessTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private UserRepository users;

	@Autowired
	private ReviewRepository reviews;

	@Autowired
	private QuestionRepository questions;

	@Test
	public void testAskQuestionUpdatesDatabase() {
		var entity = restTemplate.postForEntity("http://localhost:" + port + "/chat/askquestion", "Asking a question?",
				ChatAnswer.class);
		String cookie = entity.getHeaders().get("Set-Cookie").get(0);
		cookie = cookie.substring(cookie.indexOf('=') + 1, cookie.indexOf(';'));
		String session = new String(Base64.getDecoder().decode(cookie));

		ChatAnswer answer = entity.getBody();
		Optional<Question> question = questions.findById(answer.getQuestionId());
		assertEquals(true, question.isPresent());
		User user = question.get().getUser();
		assertEquals(true, user.getSessionId().equals(session));

		HttpHeaders headers = new HttpHeaders();
		headers.add("Cookie", entity.getHeaders().get("Set-Cookie").get(0));
		headers.setContentType(MediaType.APPLICATION_JSON);

		FeedbackRequest feedbackRequest = new FeedbackRequest(answer.getQuestionId(), true);
		HttpEntity<FeedbackRequest> feedbackRequestEntity = new HttpEntity<FeedbackRequest>(feedbackRequest, headers);
		restTemplate.put("http://localhost:" + port + "/chat/update-feedback", feedbackRequestEntity, String.class);
//		System.out.println(response);
		question = questions.findById(answer.getQuestionId());
		assertEquals(true, question.isPresent());
		assertEquals(true, question.get().isAnswered());

		ReviewPostRequest reviewPostRequest = new ReviewPostRequest(5);
		HttpEntity<ReviewPostRequest> reviewPostRequestEntity = new HttpEntity<ReviewPostRequest>(reviewPostRequest,
				headers);
		// the line below has error ReviewPostRequest cannot be constructed? WHAT?
		var reviewResponseEntity = restTemplate.postForEntity("http://localhost:" + port + "/chat/review", reviewPostRequestEntity,
				ReviewResponse.class);
		System.out.println(reviewResponseEntity);
		ReviewResponse reviewResponse = reviewResponseEntity.getBody();
		Review review = reviews.getById(reviewResponse.getReviewId());
		assertEquals(true, review != null);
	}

}
