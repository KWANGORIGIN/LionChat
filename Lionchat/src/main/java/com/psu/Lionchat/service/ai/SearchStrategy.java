package com.psu.Lionchat.service.ai;

public class SearchStrategy extends IntentStrategyAbs{
    @Override
    //Returns search string from Google
    public String doStrategy(String question) {
        return "http://www.google.com/search?q=" + question;
    }
}
