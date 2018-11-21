package com.apress.springbootrecipes.order;

import org.springframework.core.task.TaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

import static org.springframework.web.servlet.mvc.method.annotation.SseEmitter.event;

@RestController
public class OrderController {

  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @GetMapping("/orders")
  public SseEmitter orders() {
    var emitter = new SseEmitter();
    var executor = Executors.newSingleThreadExecutor();
    executor.execute(() -> {
      var orders = orderService.findAll();
      try {
        for (var order : orders) {
          randomDelay();
          var eventBuilder = event();
          emitter.send(
                  eventBuilder
                          .data(order)
                          .name("order-created")
                          .id(String.valueOf(order.hashCode())));
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
