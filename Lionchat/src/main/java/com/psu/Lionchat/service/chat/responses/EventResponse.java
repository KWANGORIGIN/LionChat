package com.psu.Lionchat.service.chat.responses;

import java.util.List;

public class EventResponse {
    private final String message;
    private final List<String> events;

    public EventResponse(String message, List<String> events)
    {
        this.message = message;
        this.events = events;
    }

    public String getMessage()
    {
        return this.message;
    }
    public List<String> getEvents()
    {
        return this.events;
    }

    public String toString()
    {
        String evts = message;
        for(String e : events)
        {
            evts += "<br>" + e;
        }

        return evts;
    }
}
