package com.apress.springbootrecipes.order;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

public class OrderGenerator {

  public Order generate() {
    var amount = ThreadLocalRandom.current().nextDouble(1000.00);
    return new Order(UUID.randomUUID().toString(), BigDecimal.valueOf(amount));
  }

}
