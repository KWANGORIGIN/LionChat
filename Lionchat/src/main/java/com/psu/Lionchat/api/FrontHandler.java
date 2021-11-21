package com.psu.Lionchat.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class FrontHandler {

    @RequestMapping("/mypage/{endpoint}")
    public String getFromEndpoint(@PathVariable("endpoint") String endpoint)
    {
        System.out.println(String.format("Forwarded to handler forwarded%s", endpoint));

        return String.format("forward:/forwarded%s", endpoint);
    }


}
