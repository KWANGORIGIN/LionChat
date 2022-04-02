package com.psu.Lionchat.service.ai;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class IntentStrategyAbs implements IntentStrategyIF{
    protected Logger strategyLogger = LoggerFactory.getLogger(this.getClass());
}
