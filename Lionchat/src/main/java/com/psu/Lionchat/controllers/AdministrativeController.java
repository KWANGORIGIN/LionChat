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
import org.springframework.web.servlet.ModelAndView;

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

	@GetMapping("/")
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("index");
		return modelAndView;
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
	 * Return all questions asked to the system and information about the users who
	 * asked them.
	 */
	@GetMapping("/questions-asked")
	void getQuestionsAsked() {
		List<Question> q = questions.findAll();
		throw new UnsupportedOperationException();
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
	 * Return the average overall 5 star rating of the system.
	 */
	@GetMapping("/average-ratings")
	double getAverageRating() {
		List<Review> rl = reviews.findAll();

		double total = 0;
		long count = 0;
		for (Review r : rl) {
			if (r.getScore() == -1) {
				continue;
			}
			total += r.getScore();
			count++;
		}

		if (count == 0) {
			return -1;
		}

		double average = total / count;
		return average;
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
	 * Return the total number of inappropriate queries asked to the system.
	 */
	@GetMapping("/number-inappropriate-queries")
	long getNumberInappropriateQueries() {
		return inappropriateQuestions.count();
	}

	/**
	 * Return the inappropriate queries and information about the users who asked
	 * them (such as ip address).
	 */
	@GetMapping("/inappropriate-queries")
	void getInappropriateQueries() {
		throw new UnsupportedOperationException();
	}
}
