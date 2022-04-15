package com.psu.Lionchat.service.chat.responses;

public class Event {
    private final String name;
    private final String organizer;
    private final String location;
    private final String date;
    private final String url;

    public Event()
    {
        this.name = null;
        this.organizer = null;
        this.location = null;
        this.date = null;
        this.url = null;
    }

    public Event(String name, String organizer, String location, String date, String url)
    {
        this.name = name;
        this.organizer = organizer;
        this.location = location;
        this.date = date;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getOrganizer() {
        return organizer;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }

    public String toString()
    {
        return "Name: " + this.name + "<br>Organizer: " + this.organizer + "<br>Location: " + this.location +
                "<br>Date: " + this.date + "<br>URL: " + this.url;
    }
}
