package com.psu.Lionchat.ai;

import org.springframework.stereotype.Service;

@Service
public class AnswerDeterminer implements AnswerDeterminerIF{
    private IntentStrategyIF intentStrategy;

    public AnswerDeterminer(IntentStrategyIF intentStrategy)
    {
        this.intentStrategy = intentStrategy;
    }
}
