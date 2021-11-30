package com.psu.Lionchat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import com.psu.Lionchat.dao.entities.Question;
import com.psu.Lionchat.dao.repositories.QuestionRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SendFeedbackEndpointTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private QuestionRepository questions;

	@Test
	public void testSendFeedbackDatabase() {
		long beforeCount = questions.count();
		restTemplate.postForObject("http://localhost:" + port + "/chat/askquestion",
				"Asking a question?", String.class);
		long afterCount = questions.count();
		
		assertEquals(beforeCount+1, afterCount);
		
		Question question = questions.getById(afterCount);
		restTemplate.postForObject("http://localhost:" + port + "/chat/feedback",
				true, String.class);
		assertEquals(true, question.isAnswered());
	}

}