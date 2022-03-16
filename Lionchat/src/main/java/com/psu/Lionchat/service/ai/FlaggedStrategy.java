package com.psu.Lionchat.service.ai;

import org.springframework.stereotype.Component;

@Component
// for naughty behavior
public class FlaggedStrategy extends IntentStrategyAbs{
    @Override
    public String doStrategy(String question) {
        return null;
    }
}
