package com.apress.springbootrecipes.demo;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.mockito.Mockito.verify;

public class HelloWorldSenderTest {

  @Test
  public void shouldSendMessageWhenMethodCalled() {
    RabbitTemplate rabbitTemplate = Mockito.mock(RabbitTemplate.class);
    HelloWorldSender sender = new HelloWorldSender(rabbitTemplate);

    sender.sendTime();

    verify(rabbitTemplate, Mockito.atLeastOnce()).convertAndSend("hello", "Hello World, from Spring Boot 2, over RabbitMQ!");

  }

}
