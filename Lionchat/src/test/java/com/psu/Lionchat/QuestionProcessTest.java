
package com.psu.Lionchat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

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
		long beforeCount = questions.count();
		restTemplate.postForObject("http://localhost:" + port + "/chat/askquestion", "Asking a question?",
				String.class);
		long afterCount = questions.count();

		assertEquals(beforeCount + 1, afterCount);

		restTemplate.postForObject("http://localhost:" + port + "/chat/askquestion", "Asking a question?",
				String.class);

		beforeCount = reviews.count();
		restTemplate.postForObject("http://localhost:" + port + "/chat/review", 5, String.class);
		afterCount = reviews.count();

		assertEquals(beforeCount + 1, afterCount);
	}

}
