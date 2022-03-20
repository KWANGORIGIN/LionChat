package com.psu.Lionchat.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psu.Lionchat.dao.entities.Question;
import com.psu.Lionchat.dao.entities.Review;
import com.psu.Lionchat.dao.repositories.CrashReportRepository;
import com.psu.Lionchat.dao.repositories.InappropriateQuestionRepository;
import com.psu.Lionchat.dao.repositories.IntentRepository;
import com.psu.Lionchat.dao.repositories.QuestionRepository;
import com.psu.Lionchat.dao.repositories.ReviewRepository;
import com.psu.Lionchat.dao.repositories.UserRepository;

/**
 * The controller for the administrative webpage. This includes endpoints for
 * getting administrative information to give a indication of how well the
 * system is doing or whether or not the system is being misused
 * 
 * @author jacobkarabin
 */
@RestController
@RequestMapping("/administrative")
// TODO: Authentication
public class AdministrativeController {
	private UserRepository users;
	private ReviewRepository reviews;
	private QuestionRepository questions;
	private IntentRepository intents;
	private InappropriateQuestionRepository inappropriateQuestions;
	private CrashReportRepository crashReports;

	@Autowired
	public AdministrativeController(UserRepository users, ReviewRepository reviews, QuestionRepository questions,
			IntentRepository intents, InappropriateQuestionRepository inappropriateQuestions) {
		super();

		this.users = users;
		this.reviews = reviews;
		this.questions = questions;
		this.intents = intents;
		this.inappropriateQuestions = inappropriateQuestions;
	}

	/**
	 * Return the crash reports and their information.
	 */
	@GetMapping("/crashreports")
	String getCrashReports(HttpSession session, HttpServletRequest request) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Return the total number of questions asked to the system as an integer.
	 */
	@GetMapping("/total-questions-asked")
	long getTotalQuestionsAsked() {
		return questions.count();
	}

	/**
	 * Return the average 5 star rating for each business defined topic.
	 * 
	 * @return
	 */
	@GetMapping("/average-ratings-per-topic")
	Map<String, Double> getAverageRatingPerTopic() {
		Map<String, Integer> counts = new HashMap<>();
		Map<String, Integer> ratings = new HashMap<>();
		for (Question q : questions.findAll()) {
			if (q.getReview() == null) {
				continue;
			}
			if (q.getIntent() == null) {
				counts.put("UnknownIntent", counts.getOrDefault("UnknownIntent", 0) + 1);
				ratings.put("UnknownIntent", ratings.getOrDefault("UnknownIntent", 0) + q.getReview().getScore());
				continue;
			}
			counts.put(q.getIntent().getIntent(), counts.getOrDefault(q.getIntent().getIntent(), 0) + 1);
			ratings.put(q.getIntent().getIntent(),
					ratings.getOrDefault(q.getIntent().getIntent(), 0) + q.getReview().getScore());
		}
		Map<String, Double> averageRatings = new HashMap<>();
		for (String s : counts.keySet()) {
			averageRatings.put(s, (double) ratings.get(s) / counts.get(s));
		}
		// sorted?
		return averageRatings;
	}

	/**
	 * Return the average overall 5 star rating of the system.
	 */
	@GetMapping("/average-ratings")
	double getAverageRating() {
		List<Review> rl = reviews.findAll();
		if (rl.size() == 0) {
			return -1;
		}

		double total = 0;
		for (Review r : rl) {
			total += r.getScore();
		}
		double average = total / reviews.count();
		return average;
	}

	/**
	 * Return the number of questions asked about each business defined topic.
	 * 
	 * @return
	 */
	@GetMapping("/number-questions-per-topic")
	Map<String, Integer> getNumberQuestionsPerTopic() {
		Map<String, Integer> map = new HashMap<>();
		for (Question q : questions.findAll()) {
			if (q.getIntent() == null) {
				map.put("UnknownIntent", map.getOrDefault("UnknownIntent", 0) + 1);
				continue;
			}
			map.put(q.getIntent().getIntent(), map.getOrDefault(q.getIntent().getIntent(), 0) + 1);
		}
		return map;
	}

	/**
	 * Return the number of misclassifications for each business defined topic. This
	 * is how many times the user said that their question was not answered.
	 * 
	 * @return
	 */
	@GetMapping("/number-misclassifications-per-topic")
	Map<String, Integer> getNumberMisclassificationsPerTopic() {
		Map<String, Integer> map = new HashMap<>();
		for (Question q : questions.findAll()) {
			if (q.isAnswered()) {
				continue;
			}
			if (q.getIntent() == null) {
				map.put("UnknownIntent", map.getOrDefault("UnknownIntent", 0) + 1);
				continue;
			}
			map.put(q.getIntent().getIntent(), map.getOrDefault(q.getIntent().getIntent(), 0) + 1);
		}
		return map;
	}

	/**
	 * Return the inappropriate queries and information about the users who asked
	 * them (such as ip address).
	 */
	@GetMapping("/inappropriate-queries")
	void getInappropriateQueries() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Return the total number of inappropriate queries asked to the system.
	 */
	@GetMapping("/number-inappropriate-queries")
	long getNumberInappropriateQueries() {
		return inappropriateQuestions.count();
	}

	/**
	 * Return all questions asked to the system and information about the users who
	 * asked them.
	 */
	@GetMapping("/questions-asked")
	void getQuestionsAsked() {
		List<Question> q = questions.findAll();
		throw new UnsupportedOperationException();
	}

}
