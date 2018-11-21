package com.apress.springbootrecipes.helloworld;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class HelloWorld {

    @Scheduled(fixedRate = 2000)
    public void sayHello() {
        System.out.println("Hello World, from Spring Boot 2!");
    }
}
