package com.apress.springbootrecipes;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class HelloWorldController {

	@GetMapping
	public Callable<String> hello() {
    return () -> {
      Thread.sleep(ThreadLocalRandom.current().nextInt(5000));
      return "Hello World, from Spring Boot 2!";
    };
  }
}
