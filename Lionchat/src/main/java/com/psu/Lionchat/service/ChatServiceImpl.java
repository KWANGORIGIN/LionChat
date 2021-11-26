package com.psu.Lionchat.service;

import com.psu.Lionchat.service.ai.AnswerDeterminerIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl extends AbstractService{
    private AnswerDeterminerIF answerDeterminer;

    @Autowired
    public ChatServiceImpl(AnswerDeterminerIF answerDeterminer)
    {
        this.answerDeterminer = answerDeterminer;
    }
}
