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
import com.psu.Lionchat.dao.repositories.QuestionRepository;
import com.psu.Lionchat.service.chat.requests.FeedbackRequest;
import com.psu.Lionchat.service.chat.responses.ChatAnswer;

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
		var entity = restTemplate.postForEntity("http://localhost:" + port + "/chat/askquestion", "Asking a question?",
				ChatAnswer.class);
		String cookie = entity.getHeaders().get("Set-Cookie").get(0);
		cookie = cookie.substring(cookie.indexOf('=') + 1, cookie.indexOf(';'));
		String session = new String(Base64.getDecoder().decode(cookie));

		ChatAnswer answer = entity.getBody();
		Optional<Question> question = questions.findById(answer.getQuestionId());
		assertEquals(true, question.isPresent());

		HttpHeaders headers = new HttpHeaders();
		headers.add("Cookie", entity.getHeaders().get("Set-Cookie").get(0));
		headers.setContentType(MediaType.APPLICATION_JSON);

		FeedbackRequest request = new FeedbackRequest(answer.getQuestionId(), true);
		HttpEntity<FeedbackRequest> feedbackRequest = new HttpEntity<FeedbackRequest>(request, headers);
		restTemplate.put("http://localhost:" + port + "/chat/update-feedback", feedbackRequest,
				String.class);
//		System.out.println(response);
		question = questions.findAll().stream().filter(q -> q.getUser().getSessionId().equals(session)).findAny();
		System.out.println(question);
		assertEquals(true, question.isPresent());
		assertEquals(true, question.get().isAnswered());
	}

}