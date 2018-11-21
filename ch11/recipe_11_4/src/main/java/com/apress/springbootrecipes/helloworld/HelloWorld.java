package com.apress.springbootrecipes.helloworld;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class HelloWorld {

    @Value("${audience:World}")
    private String audience;

    @Scheduled(fixedRate = 2000)
    public void sayHello() {
        System.out.printf("Hello %s, from Spring Boot 2!%n", audience);
    }
}
