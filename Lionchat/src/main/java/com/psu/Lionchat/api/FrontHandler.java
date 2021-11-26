package com.psu.Lionchat.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FrontHandler {

    @RequestMapping("/mypage/{endpoint}")
    public String getFromEndpoint(@PathVariable("endpoint") String endpoint)
    {
        System.out.printf("Forwarded to handler forwarded%s%n", endpoint);

        return String.format("forward:/forwarded%s", endpoint);
    }
}