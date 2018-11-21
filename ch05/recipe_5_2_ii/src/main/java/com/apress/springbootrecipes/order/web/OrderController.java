package com.apress.springbootrecipes.order.web;

import com.apress.springbootrecipes.order.Order;
import com.apress.springbootrecipes.order.OrderService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/orders")
public class OrderController {

  private final OrderService orderService;

  OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @PostMapping
  public Mono<Order> store(@RequestBody Mono<Order> order) {
    return orderService.save(order);
  }

  @GetMapping("/{id}")
  public Mono<Order> find(@PathVariable("id") String id) {
    return orderService.findById(id);
  }

  @GetMapping(produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
  public Flux<Order> list() {
    return orderService.orders();
  }
}
