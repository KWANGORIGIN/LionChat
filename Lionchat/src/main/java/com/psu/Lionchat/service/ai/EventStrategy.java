package com.psu.Lionchat.service.ai;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.psu.Lionchat.service.PythonResolver;
import com.psu.Lionchat.service.chat.requests.EventRequest;
import com.psu.Lionchat.service.chat.responses.EventResponse;

public class EventStrategy extends IntentStrategyAbs {
	@Override
	public String doStrategy(String question) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		Gson gson = new Gson();
		EventRequest utterance = new EventRequest(question);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(gson.toJson(utterance), headers);
		String url = String.format("http://%s:8000/answer_events", PythonResolver.getHostName());
		String response = restTemplate.postForObject(url, entity,
				String.class);
		EventResponse eventResponse = gson.fromJson(response, EventResponse.class);

		return eventResponse.toString();
	}
}
