package com.psu.Lionchat.service.ai;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.psu.Lionchat.service.PythonResolver;
import com.psu.Lionchat.service.chat.requests.FlaggedRequest;
import com.psu.Lionchat.service.chat.responses.FlaggedResponse;

@Component
// for naughty behavior
public class FlaggedStrategy extends IntentStrategyAbs {
	private Set<String> badWordsSet;

	@Autowired
	public FlaggedStrategy() {
		this.badWordsSet = createReferenceSet();
	}

	@Override
	public String doStrategy(String question) {
		// Preprocessing for keywords
		boolean flagQuestion = filterKeywords(question);

		// If not found flagged, then run through Python server
		if (!flagQuestion) {
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			Gson gson = new Gson();
			FlaggedRequest utterance = new FlaggedRequest(question);
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(gson.toJson(utterance), headers);
			String url = String.format("http://%s:8000/toxic_classification", PythonResolver.getHostName());
			String response = restTemplate.postForObject(url, entity, String.class);
			FlaggedResponse flaggedResponse = gson.fromJson(response, FlaggedResponse.class);

			if (flaggedResponse.getToxicity().equals("nontoxic")) {
				return "Valid";
			}
		}

		return "Please keep your language clean and appropriate. You may rephrase your query.";
	}

	private Set<String> createReferenceSet() {
		// Read bad words from file and convert to Set
		File file = new File("./txt-resources/bad-words.txt");
		List<String> badWordsList = new ArrayList<>();
		try {
			Stream<String> lines = Files.lines(Paths.get("./txt-resources/bad-words.txt"));
			badWordsList = lines.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new HashSet<>(badWordsList);
	}

	private boolean filterKeywords(String question) {
		// Convert question to Set
		question = question.replaceAll("\\s+", " ");// remove all extra spacing
		Set<String> questionSet = new HashSet<>(Arrays.asList(question.split(" ")));

		// Find intersection between sets
		questionSet.retainAll(badWordsSet);
		return questionSet.size() != 0;
	}
}
