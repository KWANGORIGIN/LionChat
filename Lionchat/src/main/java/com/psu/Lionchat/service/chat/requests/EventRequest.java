package com.psu.Lionchat.service.chat.requests;

public class EventRequest {
    private final String utterance;


    public EventRequest(String utterance)
    {
        this.utterance = utterance;
    }

    public String getutterance()
    {
        return this.utterance;
    }
}
