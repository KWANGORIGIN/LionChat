package com.psu.Lionchat.controllers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.psu.Lionchat.dao.entities.InappropriateQuestion;
import com.psu.Lionchat.dao.entities.Question;
import com.psu.Lionchat.dao.entities.Review;
import com.psu.Lionchat.dao.entities.User;
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
		return this.questions.count();
	}

	static class IntentQuestionPair {
		private Long key;
		private String intent;
		private String question;

		public IntentQuestionPair(Long key, String intent, String question) {
			super();
			this.key = key;

			if (!intent.equals("null")) {
				this.intent = intent;
			} else {
				this.intent = "Unknown_Intent";
			}

			this.question = question;
		}

		public Long getKey() {
			return key;
		}

		public String getIntent() {
			return intent;
		}

		public String getQuestion() {
			return question;
		}
	}

	/**
	 * Return all questions asked to the system and information about the users who
	 * asked them.
	 */
	@GetMapping("/questions-asked")
	List<IntentQuestionPair> getQuestionsAsked() {
		// O(inappropriateSize)
		var iids = this.inappropriateQuestions.findAll().parallelStream()
				.map(InappropriateQuestion::getQuestion)
				.map(Question::getId).collect(Collectors.toCollection(HashSet::new));

		// O(2*questionsSize)
		List<IntentQuestionPair> questions = this.questions.findAll().stream()
				.filter(q -> !iids.contains(q.getId()))
				.map(q -> new IntentQuestionPair(q.getId(), q.getIntent().getIntent(), q.getInputString()))
				.toList();

		return questions;
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
			if (q.getIntent() == null || q.getIntent().getIntent().equals("null")) {
				map.put("Unknown_Intent", map.getOrDefault("Unknown_Intent", 0) + 1);
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
		List<Review> rl = this.reviews.findAll();

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
	 * Return the number of classifications for each business defined topic. This is
	 * how many times the user said that their question was answered.
	 * 
	 * @return
	 */
	@GetMapping("/number-classifications-per-topic")
	Map<String, Integer> getNumberClassificationsPerTopic() {
		Map<String, Integer> map = new HashMap<>();
		for (Question q : this.questions.findAll()) {
			if (q.isAnswered() == null || !q.isAnswered()) {
				continue;
			}
			if (q.getIntent() == null || q.getIntent().getIntent().equals("null")) {
				map.put("Unknown_Intent", map.getOrDefault("Unknown_Intent", 0) + 1);
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
		for (Question q : this.questions.findAll()) {
			if (q.isAnswered() == null || q.isAnswered()) {
				continue;
			}
			if (q.getIntent() == null || q.getIntent().getIntent().equals("null")) {
				map.put("Unknown_Intent", map.getOrDefault("Unknown_Intent", 0) + 1);
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
		return this.inappropriateQuestions.count();
	}

	/**
	 * Return the inappropriate queries and information about the users who asked
	 * them (such as ip address).
	 */
	@GetMapping("/inappropriate-queries")
	InappropriateQueriesResponse getInappropriateQueries() {
		InappropriateQueriesResponse response = new InappropriateQueriesResponse();
		for (var i : this.inappropriateQuestions.findAll()) {
			String question = i.getQuestion().getInputString();
			User user = i.getQuestion().getUser();
			String userIp = user.getIp();
			response.addData(i.getId(), userIp, question);
		}
		return response;
//		throw new UnsupportedOperationException();
	}
}
