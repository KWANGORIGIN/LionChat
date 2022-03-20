package com.psu.Lionchat.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	int getTotalQuestionsAsked() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Return the average 5 star rating for each business defined topic.
	 */
	@GetMapping("/average-ratings-per-topic")
	void getAverageRatingPerTopic() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Return the average overall 5 star rating of the system.
	 */
	@GetMapping("/average-ratings")
	void getAverageRating() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Return the number of questions asked about each business defined topic.
	 */
	@GetMapping("/number-questions-per-topic")
	void getNumberQuestionsPerTopic() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Return the number of misclassifications for each business defined topic. This
	 * is how many times the user said that their question was not answered.
	 */
	@GetMapping("/number-misclassifications-per-topic")
	void getNumberMisclassificationsPerTopic() {
		throw new UnsupportedOperationException();
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
	void getNumberInappropriateQueries() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Return all questions asked to the system and information about the users who
	 * asked them.
	 */
	@GetMapping("/questions-asked")
	void getQuestionsAsked() {
		throw new UnsupportedOperationException();
	}

}
