package com.psu.Lionchat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.util.SocketUtils;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Execution(ExecutionMode.CONCURRENT)
public class TwoUserTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void firstUserRequest(){
//        System.out.println("FirstUserRequest => " + Thread.currentThread().getName());
        System.out.println(restTemplate.postForObject("http://localhost:" + port + "/chat/askquestion", "yeet", String.class));
//        for(int count = 0; count < 1000; count++){
//            System.out.println("First test");
//        }
    }

    @Test
    public void secondUserRequest(){
//        System.out.println("SecondUserRequest => " + Thread.currentThread().getName());
        System.out.println(restTemplate.postForObject("http://localhost:" + port + "/chat/askquestion", "yeet", String.class));
        System.out.println(restTemplate.postForObject("http://localhost:" + port + "/chat/askquestion", "yeet", String.class));
//        for(int count = 0; count < 1000; count++){
//            System.out.println("Second test");
//        }
    }

}
