package com.psu.Lionchat;
//
//import org.junit.jupiter.api.parallel.Execution;
//import org.junit.jupiter.api.parallel.ExecutionMode;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.Arguments;
//import org.junit.jupiter.params.provider.MethodSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.web.server.LocalServerPort;
//
//import java.util.stream.IntStream;
//import java.util.stream.Stream;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//@Execution(ExecutionMode.CONCURRENT)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class ThousandUserTest {
//
//    @LocalServerPort
//    private int port;
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    @ParameterizedTest
//    @MethodSource("userValueGenerator")
//    public void thousandUserRequest(){
//        String result = restTemplate.postForObject("http://localhost:" + port + "/chat/askquestion", "Asking a question?", String.class);
////        System.out.println(result + " " + Thread.currentThread().getName());
//        assertNotNull(result);
//    }
//
//    private static Stream<Arguments> userValueGenerator(){
//        return IntStream.range(0,1000).mapToObj(Arguments::of);
//    }
//}
