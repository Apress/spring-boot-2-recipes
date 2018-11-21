package com.apress.springbootrecipes.order;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class OrderService {

  private final Map<String, Order> orders = new ConcurrentHashMap<>(10);

  @PostConstruct
  public void init() {
    OrderGenerator generator = new OrderGenerator();
    for (int i = 0; i < 25; i++ ) {
      var order = generator.generate();
      orders.put(order.getId(), order);
    }
  }

  public Mono<Order> findById(String id) {
    return Mono.justOrEmpty(orders.get(id));
  }

  public Mono<Order> save(Mono<Order> order) {
    return order.map(this::save);
  }

  private Order save(Order order) {
    orders.put(order.getId(), order);
    return order;
  }

  public Flux<Order> orders() {
    return Flux.fromIterable(orders.values()).delayElements(Duration.ofMillis(128));
  }
}
