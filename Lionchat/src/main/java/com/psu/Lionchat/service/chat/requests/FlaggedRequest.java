package com.psu.Lionchat.service.chat.requests;

public class FlaggedRequest {
    private final String utterance;

    public FlaggedRequest(String question) {
        this.utterance = question;
    }

    public String getQuestion() {
        return utterance;
    }
}
