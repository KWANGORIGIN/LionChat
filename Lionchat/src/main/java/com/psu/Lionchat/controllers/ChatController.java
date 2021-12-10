package com.psu.Lionchat.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.psu.Lionchat.service.chat.ChatService;
import com.psu.Lionchat.service.chat.ChatServiceImpl;


@RestController
@RequestMapping("/chat")
// TODO: HATEOAS
/**
 * A controller for the chatbot side of the application. The endpoints a chat
 * user are here such as asking questions and rating the quality of the chatbots
 * responses.
 * 
 * @author jacobkarabin
 */
public class ChatController {
	// TODO: This should be in a service
	private ChatService chatService;

	@Autowired
	public ChatController(ChatServiceImpl chatService) {
		super();
		this.chatService = chatService;
	}

	@RequestMapping("/")
	public ModelAndView index () {
	    ModelAndView modelAndView = new ModelAndView();
	    modelAndView.setViewName("index");
	    return modelAndView;
	}
	/**
	 * Ask the system a question and receive an answer. The system will log the
	 * question as well as information about the user who asked the question. Move
	 * the user into the feedback state.
	 */
	@CrossOrigin(origins = "http://localhost:8082")
	@PostMapping("/askquestion")
	// TODO: Proper return type.
	String askQuestion(@RequestBody String question, HttpServletRequest request) {
		// make sure alphanumeric!
		// first get answer to question from python server
		// then update the state.
//		this.chatService.getAnswer(request, question);
//		return request.getRemoteAddr() + ":" + request.getRemotePort();
		return this.chatService.getAnswer(request, question);
	}

	/**
	 * Answer the systems yes/no question as to whether their question was answered.
	 * If their question was answered the system will potentially advance them to
	 * the review state. If their question was not answered provide them with
	 * helpful tips and move them back to the idle state.
	 */
	@PostMapping("/feedback")
	// TODO: Proper return type.
	String feedback(@RequestBody boolean helpful, HttpServletRequest request) {
		// first make sure correct state
		// then if answer yes move on to potential review state
		// if no, then provide helpful tips
		try {
			this.chatService.submitFeedback(request, helpful);
		}catch(Exception e) {
			return "Failed to submit feedback, illegal state.";
		}
		
		return "Added feedback";
	}

	/**
	 * Rate the system. The system will rate the last question the user asked on a 5
	 * star scale. There may be concurrency issues with this endpoint, and the user
	 * should only be able to write on review for this question. Once the rates
	 * writes the question, move them into the idle state.
	 */
	@PostMapping("/review")
	// TODO: Proper return type.
	String review(@RequestBody int score, HttpServletRequest request) {
		// first make sure correct state
		// then submit review
		// revert back to default state
		try {
			this.chatService.submitReview(request, score);
		}catch(Exception e) {
			return "Failed to submit review, illegal state.";
		}
		return "Reviewed question";
	}

}
