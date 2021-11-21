package com.psu.Lionchat.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RequestMapping("/forwardedhandler2")
@RestController
public class Handler2 {
    @GetMapping
    public String getFromHandler2(HttpSession session)
    {
        System.out.println("Session ID: " + session.getId());
        return "This is handler 2's GET request!";
    }

    @PostMapping
    public String postFromHandler2()
    {
        return "This is handler 2's POST request!";
    }
}
