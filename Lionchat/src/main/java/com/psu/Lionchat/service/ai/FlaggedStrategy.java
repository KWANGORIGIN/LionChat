package com.psu.Lionchat.service.ai;

import com.google.gson.Gson;
import com.psu.Lionchat.service.chat.requests.FlaggedRequest;
import com.psu.Lionchat.service.chat.responses.FlaggedResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
// for naughty behavior
public class FlaggedStrategy extends IntentStrategyAbs{
    @Override
    public String doStrategy(String question) {
        //Preprocessing for keywords
        boolean flagQuestion = false;

        //If not found flagged, then run through Python server
        if(!flagQuestion){
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            Gson gson = new Gson();
            FlaggedRequest utterance = new FlaggedRequest(question);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<String>(gson.toJson(utterance), headers);
            String response = restTemplate.postForObject("http://localhost:8000/intent", entity,
                    String.class);
            FlaggedResponse flaggedResponse = gson.fromJson(response, FlaggedResponse.class);

            if(flaggedResponse.getToxicity().equals("nontoxic"))
            {
                return "Valid";
            }
        }

        return "Please keep your language clean and appropriate. You may rephrase your query.";
    }
}
