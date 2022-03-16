package com.psu.Lionchat.service.ai;

import java.util.Objects;

public class StrategyFactory {
    public IntentStrategyIF getStrategy(String strategyType){
        if(Objects.equals(strategyType, "IT_Intent")){
            return new ITStrategy();
        }
        else if(Objects.equals(strategyType, "Flagged_Intent")){
            return new FlaggedStrategy();
        }
        else if(Objects.equals(strategyType, "Event_Intent")){
            return new EventStrategy();
        }
        else{
            return new InvalidStrategy();
        }
    }
}