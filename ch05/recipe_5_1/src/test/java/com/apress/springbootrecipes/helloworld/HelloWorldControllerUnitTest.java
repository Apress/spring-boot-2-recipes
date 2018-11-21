package com.apress.springbootrecipes.helloworld;

import org.junit.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class HelloWorldControllerUnitTest {

  private final HelloWorldController controller = new HelloWorldController();

  @Test
  public void shouldSayHello() {
    Mono<String> result = controller.hello();

    StepVerifier.create(result)
            .expectNext("Hello World, from Reactive Spring Boot 2!")
            .verifyComplete();
  }

}
