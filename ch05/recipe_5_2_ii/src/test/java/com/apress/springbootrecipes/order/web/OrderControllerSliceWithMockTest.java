package com.apress.springbootrecipes.order.web;

import com.apress.springbootrecipes.order.Order;
import com.apress.springbootrecipes.order.OrderGenerator;
import com.apress.springbootrecipes.order.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SynchronousSink;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebFluxTest(OrderController.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class OrderControllerSliceWithMockTest {

  @Autowired
  private WebTestClient webTestClient;

  @MockBean
  private OrderService orderService;

  @Test
  public void listOrders() {
    var generator = new OrderGenerator();
    var orders = Flux.generate((SynchronousSink<Order> orderSynchronousSink) -> orderSynchronousSink.next(generator.generate())).take(10);
    when(orderService.orders()).thenReturn(orders);

    webTestClient.get().uri("/orders")
            .exchange()
              .expectStatus().isOk()
              .expectBodyList(Order.class).hasSize(10);
  }

  @Test
  public void addAndGetOrder() {
    var order = new Order("test1", BigDecimal.valueOf(1234.56));

    when(orderService.save(any())).thenAnswer((Answer<Mono<Order>>) invocation -> invocation.getArgument(0));
    when(orderService.findById(order.getId())).thenReturn(Mono.just(order));

    webTestClient.post().uri("/orders").syncBody(order)
            .exchange()
              .expectStatus().isOk()
              .expectBody(Order.class).isEqualTo(order);

    webTestClient.get().uri("/orders/{id}", order.getId())
            .exchange()
            .expectStatus().isOk()
            .expectBody(Order.class).isEqualTo(order);
  }
}
