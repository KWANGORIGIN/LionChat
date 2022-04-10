package com.psu.Lionchat.service.ai;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.psu.Lionchat.dao.entities.Question;
import com.psu.Lionchat.service.PythonResolver;
import com.psu.Lionchat.service.chat.requests.ClassifierRequest;
import com.psu.Lionchat.service.chat.responses.ChatAnswer;
import com.psu.Lionchat.service.chat.responses.ClassifierResponse;

@Service
public class AnswerDeterminer implements AnswerDeterminerIF {
//    private IntentStrategyIF intentClassifier;
	private IntentStrategyIF issueClassifier;

	@Autowired
	/*
	 * Consider getting rid of this being passed in and create a factory to create
	 * IntentStrategyIF
	 */
	public AnswerDeterminer(FlaggedStrategy issueClassifier) {
		this.issueClassifier = issueClassifier;// Dependency injecting FlaggedStrategy object
	}

	@Override
	public ChatAnswer getAnswer(Question questionObj) {
		// First check if there is a toxic problem with user's input
		String question = questionObj.getInputString();
		Long questionId = questionObj.getId();
		String issueResponse = issueClassifier.doStrategy(question);
		// Temporary
		// issueResponse = "Valid";//ToDo: Implement toxic classifier and get rid of
		// this
		if (Objects.equals(issueResponse, "Valid")) {
			String intentType = classifyIntent(question);
			IntentStrategyIF strategy = StrategyFactory.getStrategy(intentType);
			return new ChatAnswer(questionId, strategy.doStrategy(question), intentType);// Returns answer as determined
																							// by classifier
		}
		return new ChatAnswer(questionId, issueResponse);// Returns error message from issueClassifier
	}

	// Todo: Considering loading in Python server ip from config file
	private String classifyIntent(String question) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		Gson gson = new Gson();
		ClassifierRequest utterance = new ClassifierRequest(question);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(gson.toJson(utterance), headers);
		// TODO: make this into a bean.xml / web.xml data source if thats a thing.
		String url = String.format("http://%s:8000/intent", PythonResolver.getHostName());
		String response = restTemplate.postForObject(url, entity,
				String.class);
		ClassifierResponse classifierResponse = gson.fromJson(response, ClassifierResponse.class);
		return classifierResponse.getIntent();
	}
}
