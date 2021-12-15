package com.psu.Lionchat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.transaction.annotation.Transactional;

import com.psu.Lionchat.dao.repositories.QuestionRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Base64;;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GetAnswerEndpointTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	
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
		boolean present = questions.findAll().stream().filter(q->q.getUser().getSessionId().equals(session)).findAny().isPresent();
		assertEquals(true, present);
	}

}
