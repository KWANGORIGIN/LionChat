package com.psu.Lionchat.service.chat;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.psu.Lionchat.dao.entities.Question;
import com.psu.Lionchat.dao.entities.Review;
import com.psu.Lionchat.dao.entities.User;
import com.psu.Lionchat.dao.repositories.InappropriateQuestionRepository;
import com.psu.Lionchat.dao.repositories.QuestionRepository;
import com.psu.Lionchat.dao.repositories.ReviewRepository;
import com.psu.Lionchat.dao.repositories.UserRepository;
import com.psu.Lionchat.service.ConversationState;

@Service
public class ChatServiceImpl implements ChatService {
	private UserRepository users;
	private ReviewRepository reviews;
	private QuestionRepository questions;
	private InappropriateQuestionRepository inappropriateQuestions;
	
	@Autowired
	public ChatServiceImpl(UserRepository users, ReviewRepository reviews, QuestionRepository questions,
			InappropriateQuestionRepository inappropriateQuestions) {
		super();

		this.users = users;
		this.reviews = reviews;
		this.questions = questions;
		this.inappropriateQuestions = inappropriateQuestions;
	}

	private String classify(String question) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		Gson gson = new Gson();
		Map<String, String> map = new HashMap<>();

		map.put("utterance", question);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(gson.toJson(map), headers);

		//TODO: make this into a bean.xml / web.xml data source if thats a thing.
		String response = restTemplate.postForObject("http://localhost:8000/intent", entity, String.class);
		Map<String, String> responseMap = gson.fromJson(response, Map.class);
		return responseMap.get("intent");
	}
	
	private User getUser(HttpServletRequest request) {
		String sessionId = request.getSession().getId();
		Optional<User> userOptional = users.findBySessionId(sessionId);

		if (userOptional.isPresent()) {
			return userOptional.get();
		}
		
		User user = new User(sessionId, request.getRemoteAddr());
		users.save(user);

		return user;
	}

	@Override
	public String getAnswer(HttpServletRequest request, String question) {
		User user = this.getUser(request);
		Question q = new Question(user, question, false);
		HttpSession session = request.getSession();
		
		
		// TODO: Concurrency Issue
		// TODO: Do we need to save this user's current question ID?
		session.setAttribute(ConversationState.class.getName(), ConversationState.FEEDBACK);
		session.setAttribute(Question.class.getName(), q);
		
		questions.save(q);
		

		return this.classify(question);
				//"Sorry, I do not understand. Please try to rephrase the question.";//"I have discovered a truly remarkable answer of this question which this margin is too small to contain";
	}

	@Override
	public void submitFeedback(HttpServletRequest request, boolean helpful) {
		// save user if doesn't exist?
		this.getUser(request);
		HttpSession session = request.getSession();
		
		// TODO: Custom exception and concurrency issue
		if(session.getAttribute(ConversationState.class.getName()) != ConversationState.FEEDBACK) {
			throw new IllegalStateException();
		}
		
		Question question = (Question) session.getAttribute(Question.class.getName());
		question.setAnswered(helpful);
		questions.save(question);
		
		// TODO: Concurrency issue
		session.setAttribute(ConversationState.class.getName(), ConversationState.RATING);
	}

	@Override
	public void submitReview(HttpServletRequest request, int rating) {
		// save user if doesn't exist?
		this.getUser(request);
		HttpSession session = request.getSession();
		
		// TODO: Custom exception and concurrency issue
		if(session.getAttribute(ConversationState.class.getName()) != ConversationState.RATING) {
			throw new IllegalStateException();
		}
		
		Question question = (Question) session.getAttribute(Question.class.getName());
		Review review = new Review(question, rating);
		reviews.save(review);
		
		// TODO: Concurrency issue
		session.setAttribute(ConversationState.class.getName(), ConversationState.IDLE);
	}

}