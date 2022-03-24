package com.psu.Lionchat.service.chat.responses;

public class FlaggedResponse {
    private final String toxicity;

    public FlaggedResponse(String toxicity)
    {
        this.toxicity = toxicity;
    }

    public String getToxicity()
    {
        return this.toxicity;
    }
}
