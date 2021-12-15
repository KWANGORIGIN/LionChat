
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
import com.psu.Lionchat.dao.repositories.QuestionRepository;
import com.psu.Lionchat.dao.repositories.ReviewRepository;;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class QuestionProcessTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private ReviewRepository reviews;

	@Autowired
	private QuestionRepository questions;

	@Test
	public void testAskQuestionUpdatesDatabase() {
		var entity = restTemplate.postForEntity(
				"http://localhost:" + port + "/chat/askquestion",
				"Asking a question?", String.class);
		String cookie = entity.getHeaders().get("Set-Cookie").get(0);
		cookie = cookie.substring(cookie.indexOf('=')+1, cookie.indexOf(';'));
		String session = new String(Base64.getDecoder().decode(cookie));
		Optional<Question> question = questions.findAll().stream().filter(q->q.getUser().getSessionId().equals(session)).findAny();
		assertEquals(true, question.isPresent());
		
//		System.out.println(entity.getBody());
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Cookie", entity.getHeaders().get("Set-Cookie").get(0));
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Boolean> feedbackRequest = new HttpEntity<Boolean>(true, headers);
		String response = restTemplate.postForObject(
				"http://localhost:" + port + "/chat/feedback", feedbackRequest,
				String.class);
//		System.out.println(response);
		question = questions.findAll().stream().filter(q->q.getUser().getSessionId().equals(session)).findAny();
		assertEquals(true, question.isPresent());
		assertEquals(true, question.get().isAnswered());

		HttpEntity<Integer> reviewsRequest = new HttpEntity<Integer>(5, headers);
		response = restTemplate.postForObject("http://localhost:" + port + "/chat/review",
				reviewsRequest, String.class);
//		System.out.println(response);

		Optional<Review> review = reviews.findAll().stream().filter(r->r.getQuestion().getUser().getSessionId().equals(session)).findAny();
		assertEquals(true, review.isPresent());
	}

}
