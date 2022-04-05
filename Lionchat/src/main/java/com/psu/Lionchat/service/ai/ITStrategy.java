package com.psu.Lionchat.service.ai;

import com.google.gson.Gson;
import com.psu.Lionchat.service.chat.requests.SimilarityRequest;
import com.psu.Lionchat.service.chat.responses.SimilarityResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ITStrategy extends IntentStrategyAbs{
    @Override
    public String doStrategy(String question) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        Gson gson = new Gson();
        SimilarityRequest utterance = new SimilarityRequest(question);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(gson.toJson(utterance), headers);
        // TODO: make this into a bean.xml / web.xml data source if thats a thing.
        String response = restTemplate.postForObject("http://pythonserver:8000/semantic-search-results", entity,
                String.class);
        SimilarityResponse articles = gson.fromJson(response, SimilarityResponse.class);
        // ClassifierResponse intent = gson.fromJson(response,
        // ClassifierResponse.class);
        if (articles.getTitles().size() == 0) {
            return "Sorry we were unable to find any related articles to your query.";
        }
        String title = articles.getTitles().get(0);
        String url = articles.getUrls().get(0);
        return "I found this article that may help: " + String.format("<a href=%s target=\"_blank\">%s</a>", url, title);
    }
}
