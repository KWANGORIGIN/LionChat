package com.psu.Lionchat.service.ai;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary //Consider getting rid of this. Currently a temporary bug fix for AnswerDeterminer unable to find unique bean exception
@Component
// for unrecognized queries
public class InvalidStrategy extends IntentStrategyAbs{
    @Override
    public String doStrategy(String question) {
        return "I'm sorry can you please rephrase the question? We want to make sure we can provide you with the right answer.";
    }
}
