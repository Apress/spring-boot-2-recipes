package com.apress.springbootrecipes.helloworld;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.reactive.context.ReactiveWebApplicationContext;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureWebTestClient
public class HelloWorldControllerIntegrationTest {

  @Autowired
  private WebTestClient webClient;

  @Test
  public void shouldSayHello() {

    webClient.get().uri("/").accept(MediaType.TEXT_PLAIN)
            .exchange()
              .expectStatus().isOk()
              .expectBody(String.class)
            .isEqualTo("Hello World, from Reactive Spring Boot 2!");
  }
}
