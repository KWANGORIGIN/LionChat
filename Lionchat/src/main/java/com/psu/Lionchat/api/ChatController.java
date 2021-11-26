package com.psu.Lionchat.api;

import com.psu.Lionchat.service.AbstractService;
import com.psu.Lionchat.service.ChatServiceImpl;
import com.psu.Lionchat.service.FeedbackService;
import com.psu.Lionchat.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private final ChatServiceImpl chatService;
    private final RatingService ratingService;
    private final FeedbackService feedbackService;

    @Autowired
    public ChatController(ChatServiceImpl chatService, RatingService ratingService, FeedbackService feedbackService)
    {
        this.chatService = chatService;
        this.ratingService = ratingService;
        this.feedbackService = feedbackService;
    }


    // add mappings for requests
}
