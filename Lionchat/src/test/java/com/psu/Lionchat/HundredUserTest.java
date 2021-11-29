package com.psu.Lionchat;

import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.ArrayList;

@Execution(ExecutionMode.CONCURRENT)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HundredUserTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void hundredUserRequest(){
        ArrayList<Thread> threads = new ArrayList<>();
        for(int user = 0; user < 100; user++) {
            Thread userThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("HundredUserRequest => " + Thread.currentThread().getName());
                }
            });
            userThread.start();
            threads.add(userThread);
        }

        try{
            for(Thread user : threads){
                user.join();
            }
        }catch(InterruptedException e){
            System.out.println("Interrupted Exception");
        }
        
    }
}
