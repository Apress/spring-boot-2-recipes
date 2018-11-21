package com.apress.springbootrecipes.order.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureWebTestClient
public class OrderControllerIntegrationTest {

  @Autowired
  private WebTestClient webTestClient;

  @Test
  public void showOrdersIndexPage() {

    webTestClient.get().uri("/orders")
            .exchange()
              .expectHeader().contentTypeCompatibleWith(MediaType.TEXT_HTML)
              .expectStatus().isOk();
  }
}
