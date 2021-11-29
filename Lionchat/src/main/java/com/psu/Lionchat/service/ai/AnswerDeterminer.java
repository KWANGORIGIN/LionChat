package com.psu.Lionchat.service.ai;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerDeterminer implements AnswerDeterminerIF{
    private IntentStrategyIF intentStrategy;

    @Autowired
    /*
    Consider getting rid of this being passed in and create a factory to create IntentStrategyIF
     */
    public AnswerDeterminer(IntentStrategyIF intentStrategy)
    {
        this.intentStrategy = intentStrategy;
    }
}
