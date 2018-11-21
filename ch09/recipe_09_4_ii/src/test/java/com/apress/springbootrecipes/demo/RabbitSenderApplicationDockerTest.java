package com.apress.springbootrecipes.demo;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.GenericContainer;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This will bootstrap RabbitMQ through Docker.
 *
 * See {@link <a href="http://testcontainers.org">Testcontainers<a>}
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        RabbitSenderApplication.class,
        RabbitSenderApplicationIntegrationTestConfiguration.class })
@ContextConfiguration(initializers = RabbitSenderApplicationDockerTest.Initializer.class)
public class RabbitSenderApplicationDockerTest {

  @ClassRule
  public static GenericContainer rabbitmq =
          new GenericContainer("rabbitmq:3.7-management-alpine").withExposedPorts(5672);

  public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
      TestPropertyValues values = TestPropertyValues.of(
              "spring.rabbitmq.host=" + rabbitmq.getContainerIpAddress(),
              "spring.rabbitmq.port=" + rabbitmq.getMappedPort(5672)
      );
      values.applyTo(configurableApplicationContext);
    }
  }

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
