package com.psu.Lionchat.service.ai;

import org.springframework.stereotype.Component;

@Component
// for naughty behavior
public class FlaggedStrategy extends IntentStrategyAbs{
    @Override
    public String doStrategy(String question) {
        //Preprocessing for keywords
        boolean flagQuestion = false;

        //If not found flagged, then run through Python server
        if(flagQuestion){

        }

        return "Please keep your language clean and appropriate. You may rephrase your query.";
    }
}
