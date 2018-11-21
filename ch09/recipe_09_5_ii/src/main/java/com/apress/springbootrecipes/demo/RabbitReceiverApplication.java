package com.apress.springbootrecipes.demo;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Marten Deinum
 */
@SpringBootApplication
@EnableScheduling
public class RabbitReceiverApplication {

    public static void main(String[] args) {
			SpringApplication.run(RabbitReceiverApplication.class, args);
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
      return new Jackson2JsonMessageConverter();
    }
}

@Component
class OrderService {

  @RabbitListener(bindings =
    @QueueBinding(
          exchange =@Exchange(name="orders", type = ExchangeTypes.FANOUT),
          value = @Queue(name = "incoming-orders"),
          key = "new-order"
  ))
  public void handle(Order order) {
    System.out.println("[RECEIVED] - " + order);
  }
}

@Component
class OrderSender {


  private final RabbitTemplate rabbit;

  OrderSender(RabbitTemplate rabbit) {
    this.rabbit = rabbit;
  }

  @Scheduled(fixedRate = 256)
  public void sendTime() {

    var id = UUID.randomUUID().toString();
    var amount = ThreadLocalRandom.current().nextDouble(1000.00d);
    var order = new Order(id, BigDecimal.valueOf(amount));
    rabbit.convertAndSend("orders", "new-order", order);
  }

}
