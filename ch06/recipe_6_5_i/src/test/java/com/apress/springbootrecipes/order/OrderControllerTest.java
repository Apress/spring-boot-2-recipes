package com.apress.springbootrecipes.order;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderControllerTest {

  @Autowired
  private WebTestClient webTestClient;


  @Test
  public void foo() {

    webTestClient.get().uri("/orders")
            .exchange()
              .expectStatus().isOk();
  }

}
