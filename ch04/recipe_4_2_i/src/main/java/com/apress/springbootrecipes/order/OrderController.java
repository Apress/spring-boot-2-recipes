package com.apress.springbootrecipes.order;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class OrderController {


  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @GetMapping("/orders")
  public ResponseBodyEmitter orders() {
    var emitter = new ResponseBodyEmitter();
    var executor = Executors.newSingleThreadExecutor();
    executor.execute(() -> {
      Iterable<Order> orders = orderService.findAll();
      try {
        for (Order order : orders) {
          randomDelay();
          emitter.send(order);
          emitter.send("\r\n", MediaType.TEXT_PLAIN);
        }
        emitter.complete();
      } catch (IOException e) {
        emitter.completeWithError(e);
      }
    });
    executor.shutdown();
    return emitter;
  }

  private void randomDelay() {
    try {
      Thread.sleep(ThreadLocalRandom.current().nextInt(150));
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
}
