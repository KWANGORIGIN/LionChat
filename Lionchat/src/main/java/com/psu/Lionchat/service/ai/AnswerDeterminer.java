package com.psu.Lionchat.service.ai;

import org.springframework.stereotype.Component;

@Component
public class AnswerDeterminer implements AnswerDeterminerIF{
    private IntentStrategyIF intentStrategy;

    public AnswerDeterminer(IntentStrategyIF intentStrategy)
    {
        this.intentStrategy = intentStrategy;
    }
}
