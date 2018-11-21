package com.apress.springbootrecipes.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This will bootstrap an embedded RabbitMQ.
 *
 * NOTE: Requiers Erlang to be installed on the system!
 *
 * @see RabbitSenderApplicationIntegrationTestConfiguration
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        RabbitSenderApplication.class,
        RabbitSenderApplicationIntegrationTestConfiguration.class })
public class RabbitSenderApplicationIntegrationTest {

  @Autowired
  private RabbitTemplate rabbitTemplate;

  @Test
  public void shouldSendAtLeastASingleMessage() {

    String msg = (String) rabbitTemplate.receiveAndConvert("hello", 1500);
    assertThat(msg).isEqualTo("Hello World, from Spring Boot 2, over RabbitMQ!");

  }

}
