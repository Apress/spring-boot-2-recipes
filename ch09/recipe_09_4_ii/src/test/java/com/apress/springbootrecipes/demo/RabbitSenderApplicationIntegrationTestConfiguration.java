package com.apress.springbootrecipes.demo;

import io.arivera.oss.embedded.rabbitmq.EmbeddedRabbitMq;
import io.arivera.oss.embedded.rabbitmq.EmbeddedRabbitMqConfig;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Configuration class which needs to be loaded additionally to the
 * {@code @SpringBootApplication} annotated class. It will bootstrap a proces
 * which will run activemq.
 *
 * Using an {@Configuration} class makes Spring manage the starting and
 * stopping of the embedded RabbitMQ. Another option is to use the {@code @BeforeClass}
 * and {@code @AfterClass} of a test.
 *
 * The configuration includes the queue, exchanage and binding definitions
 * else it isn't possible to receive the messages.
 *
 * See {@link <a href="https://github.com/AlejandroRivera/embedded-rabbitmq">Embedded RabbitMQ</a>}
 */
@TestConfiguration
public class RabbitSenderApplicationIntegrationTestConfiguration {

  @Bean(initMethod = "start", destroyMethod = "stop")
  @Profile("embedded")
  public EmbeddedRabbitMq embeddedRabbitMq() {
    EmbeddedRabbitMqConfig config = new EmbeddedRabbitMqConfig.Builder()
            .rabbitMqServerInitializationTimeoutInMillis(10000).build();
    return new EmbeddedRabbitMq(config);
  }

  @Bean
  public Queue newOrderQueue() {
    return QueueBuilder.durable("new-order").build();
  }

  @Bean
  public Exchange ordersExchange() {
    return ExchangeBuilder.topicExchange("orders").durable(true).build();
  }

  @Bean
  public Binding newOrderQueueBinding(Queue queue, Exchange exchange) {
    return BindingBuilder.bind(queue).to(exchange).with("new-order").noargs();
  }
}
