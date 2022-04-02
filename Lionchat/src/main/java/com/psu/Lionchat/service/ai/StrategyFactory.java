package com.psu.Lionchat.service.ai;

import java.util.Objects;

public class StrategyFactory {
    public static IntentStrategyIF getStrategy(String strategyType){
        if(Objects.equals(strategyType, "IT_Intent")){
            return new ITStrategy();
        }
        else if(Objects.equals(strategyType, "Flagged_Intent")){
            return new FlaggedStrategy();
        }
        else if(Objects.equals(strategyType, "Event_Intent")){
            return new EventStrategy();
        }
        else if (Objects.equals(strategyType, "Search_Intent")){
            return new SearchStrategy();
        }
        else{
            return new InvalidStrategy();
        }
    }
}
