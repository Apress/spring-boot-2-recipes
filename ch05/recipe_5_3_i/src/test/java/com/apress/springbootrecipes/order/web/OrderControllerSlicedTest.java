package com.apress.springbootrecipes.order.web;

import com.apress.springbootrecipes.order.OrderGenerator;
import com.apress.springbootrecipes.order.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebFluxTest(OrderController.class)
public class OrderControllerSlicedTest {

  @Autowired
  private WebTestClient webTestClient;

  @MockBean
  private OrderService orderService;

  private OrderGenerator generator = new OrderGenerator();

  @Test
  public void showOrdersIndexPage() {

    when(orderService.orders()).thenReturn(Flux.just(generator.generate(), generator.generate()));

    webTestClient.get().uri("/orders")
            .exchange()
            .expectHeader().contentTypeCompatibleWith(MediaType.TEXT_HTML)
            .expectStatus().isOk();
  }
}
