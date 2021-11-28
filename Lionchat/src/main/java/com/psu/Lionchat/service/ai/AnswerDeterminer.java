package com.psu.Lionchat.service.ai;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerDeterminer implements AnswerDeterminerIF{
    private IntentStrategyIF intentStrategy;

    public AnswerDeterminer(IntentStrategyIF intentStrategy)
    {
        this.intentStrategy = intentStrategy;
    }
}
