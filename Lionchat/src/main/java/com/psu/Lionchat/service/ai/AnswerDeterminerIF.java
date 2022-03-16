package com.psu.Lionchat.service.ai;

import com.psu.Lionchat.dao.entities.Question;
import com.psu.Lionchat.service.chat.responses.ChatAnswer;

public interface AnswerDeterminerIF {
    ChatAnswer getAnswer(Question questionObj);
}
