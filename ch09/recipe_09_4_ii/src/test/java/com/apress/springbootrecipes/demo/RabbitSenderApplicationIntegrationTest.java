package com.apress.springbootrecipes.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
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
@ActiveProfiles("embedded")
public class RabbitSenderApplicationIntegrationTest {

  @Autowired
  private RabbitTemplate rabbitTemplate;

  @Test
  public void shouldSendAtLeastASingleMessage() {

    Message msg = rabbitTemplate.receive("new-order", 1500);

    assertThat(msg).isNotNull();
    assertThat(msg.getBody()).isNotEmpty();
    assertThat(msg.getMessageProperties().getReceivedExchange())
            .isEqualTo("orders");
    assertThat(msg.getMessageProperties().getReceivedRoutingKey())
            .isEqualTo("new-order");
    assertThat(msg.getMessageProperties().getContentType())
            .isEqualTo("application/json");
  }
}
