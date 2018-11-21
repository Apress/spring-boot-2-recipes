package com.apress.springbootrecipes.order;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class OrderService {

  private final List<Order> orders = new ArrayList<>();

  @PostConstruct
  public void setup() {
    createOrders();
  }

  public Iterable<Order> findAll() {
    return List.copyOf(orders);
  }


  private Iterable<Order> createOrders() {
    for (int i = 0; i < 25; i++) {
      this.orders.add(createOrder());
    }
    return orders;
  }

  private Order createOrder() {
    String id = UUID.randomUUID().toString();
    double amount = ThreadLocalRandom.current().nextDouble(1000.00d);
    return new Order(id, BigDecimal.valueOf(amount));
  }
}
