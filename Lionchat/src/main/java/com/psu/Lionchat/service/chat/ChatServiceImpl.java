package com.psu.Lionchat.service.chat;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.psu.Lionchat.dao.entities.Intent;
import com.psu.Lionchat.dao.entities.Question;
import com.psu.Lionchat.dao.entities.Review;
import com.psu.Lionchat.dao.entities.User;
import com.psu.Lionchat.dao.repositories.InappropriateQuestionRepository;
import com.psu.Lionchat.dao.repositories.IntentRepository;
import com.psu.Lionchat.dao.repositories.QuestionRepository;
import com.psu.Lionchat.dao.repositories.ReviewRepository;
import com.psu.Lionchat.dao.repositories.UserRepository;
import com.psu.Lionchat.service.PythonResolver;
import com.psu.Lionchat.service.ai.AnswerDeterminer;
import com.psu.Lionchat.service.ai.AnswerDeterminerIF;
import com.psu.Lionchat.service.chat.requests.ClassifierRequest;
import com.psu.Lionchat.service.chat.requests.FeedbackRequest;
import com.psu.Lionchat.service.chat.requests.ReviewPutRequest;
import com.psu.Lionchat.service.chat.requests.SimilarityRequest;
import com.psu.Lionchat.service.chat.responses.AskQuestionResponse;
import com.psu.Lionchat.service.chat.responses.ChatAnswer;
import com.psu.Lionchat.service.chat.responses.ClassifierResponse;
import com.psu.Lionchat.service.chat.responses.SimilarityResponse;

@Service
public class ChatServiceImpl implements ChatService {
	private UserRepository users;
	private ReviewRepository reviews;
	private QuestionRepository questions;
	private IntentRepository intents;
	private AnswerDeterminerIF answerDeterminer;

	@Autowired
	public ChatServiceImpl(UserRepository users, ReviewRepository reviews, QuestionRepository questions,
			IntentRepository intents, AnswerDeterminer answerDeterminer) {
		super();

		this.users = users;
		this.reviews = reviews;
		this.questions = questions;
		this.intents = intents;
		this.answerDeterminer = answerDeterminer;
	}

	private String itSimilarity(String question) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		Gson gson = new Gson();
		SimilarityRequest utterance = new SimilarityRequest(question);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(gson.toJson(utterance), headers);
		// TODO: make this into a bean.xml / web.xml data source if thats a
		// thing.
		String url = String.format("http://%s:8000/semantic-search-results", PythonResolver.getHostName());
		String response = restTemplate.postForObject(url, entity, String.class);
		SimilarityResponse articles = gson.fromJson(response, SimilarityResponse.class);
		// ClassifierResponse intent = gson.fromJson(response,
		// ClassifierResponse.class);
		if (articles.getTitles().size() == 0) {
			return "ERROR";
		}
		String title = articles.getTitles().get(0);
		url = articles.getUrls().get(0);
		return String.format("<a href=%s target=\"_blank\">%s</a>", url, title);
	}

	private String classify(String question) throws RestClientException, JsonSyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		Gson gson = new Gson();
		ClassifierRequest utterance = new ClassifierRequest(question);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(gson.toJson(utterance), headers);

		// TODO: make this into a bean.xml / web.xml data source if thats a
		// thing.
		String url = String.format("http://%s:8000/intent", PythonResolver.getHostName());
		String response = restTemplate.postForObject(url, entity, String.class);
		ClassifierResponse intent = gson.fromJson(response, ClassifierResponse.class);
		return intent.getIntent();
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
	public AskQuestionResponse getAnswer(HttpServletRequest request, String question) {
		User user = this.getUser(request);
		Question q = new Question(user, null, question);
		Intent intent = new Intent("null");
		q.setIntent(intent);
		intents.save(intent);
		questions.save(q);

		try {
			ChatAnswer answer = answerDeterminer.getAnswer(q);

			intent.setIntent(answer.getIntent());
			intents.save(intent);

			System.out.println("Answer: " + answer.getAnswer());

			long reviewId = -1;
			if (user.getQuestions().size() > 3) {
				if (user.getReviews().size() == 0 || user.getReviews().get(user.getReviews().size() - 1)
						.getCreationTime().plusWeeks(2).isBefore(LocalDateTime.now())) {
					// Randomly ask for reviews once the user asks 3 questions.
					// 20% chance per question asked
					if (Math.random() > .20) {
						Review review = new Review(user, -1);
						reviews.save(review);

						reviewId = review.getId();
					}
				}
			}

			// determine if user should review the system...
			return new AskQuestionResponse(answer, reviewId, false);
		} catch (Exception e) {
			e.printStackTrace();

			ChatAnswer answer = new ChatAnswer(q.getId(),
					"We cannot answer your question because our classification service is offline. We apologize for the inconvenience.");
			return new AskQuestionResponse(answer, -1, true);
		}

		/*
		 * Move below code into AnswerDeterminer class
		 */

		// try {
		// return answerDeterminer.getAnswer(q);
		//// String intentString = this.classify(question);
		//// Intent intent = new Intent(intentString);
		//// intents.save(intent);
		////
		//// q.setIntent(intent);
		//
		//// return intentString;
		//// return new ChatAnswer(q.getId(), "I found this article that may
		// help: " + this.itSimilarity(question));
		// } catch (RestClientException e) {
		// System.out.println("Failed to connect to python server.");
		// e.printStackTrace();
		// return new ChatAnswer(q.getId(),
		// "We cannot answer your question because our classification service is
		// offline. We apologize for the inconvenience.");
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		//
		// return new ChatAnswer(q.getId(), "failed");
	}

	@Override
	public void submitFeedback(HttpServletRequest request, FeedbackRequest feedbackRequest) {
		User user = this.getUser(request);
		Question question = questions.getById(feedbackRequest.getQuestionId());

		// Make sure the user asked this question.
		if (!question.getUser().equals(user)) {
			throw new IllegalStateException();
		}

		question.setAnswered(feedbackRequest.isHelpful());
		questions.save(question);
	}

	// TODO: Remove IllegalStateExceptions.
	/**
	 * Add a review to the database if the oldest review is either undefined or
	 * greater than 2 weeks old. The user must have asked at least one question. The
	 * review must be in the range [1,5].
	 */
	@Override
	public long submitReview(HttpServletRequest request, int score) {
		User user = this.getUser(request);

		if (score < 1 || score > 5) {
			// fail since the review is outside of the range.
			throw new IllegalStateException();
		}

		if (user.getQuestions().size() == 0) {
			// fail since the user asked no questions.
			throw new IllegalStateException();
		}

		if (user.getReviews().size() != 0 && !user.getReviews().get(user.getReviews().size() - 1).getCreationTime()
				.plusWeeks(2).isBefore(LocalDateTime.now())) {
			// fail since the review is too recent.
			throw new IllegalStateException();
		}

		Review review = new Review(user, score);
		reviews.save(review);

		long response = review.getId();
		return response;
	}

	@Override
	public void updateReview(HttpServletRequest request, ReviewPutRequest reviewPutRequest) {
		User user = this.getUser(request);
		Optional<Review> reviewOptional = reviews.findById(reviewPutRequest.getReviewId());

		int score = reviewPutRequest.getReview();
		if (score < 1 || score > 5) {
			// fail since the review is outside of the range.
			throw new IllegalStateException();
		}

		if (!reviewOptional.isPresent()) {
			// review was not found in the database and cannot be updated.
			throw new IllegalStateException();
		}

		Review review = reviewOptional.get();
		if (!review.getUser().equals(user)) {
			// cannot update another user's review
			throw new IllegalStateException();
		}

		review.setScore(score);
		reviews.save(review);
	}

}
