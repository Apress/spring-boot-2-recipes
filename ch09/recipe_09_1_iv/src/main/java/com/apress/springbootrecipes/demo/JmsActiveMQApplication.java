package com.apress.springbootrecipes.demo;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.jms.ConnectionFactory;

/**
 * @author Marten Deinum
 */
@SpringBootApplication
public class JmsActiveMQApplication {

    private static final String MSG = "\tName: %100s, Type: %s\n";

    public static void main(String[] args) {
        var ctx =
                SpringApplication.run(JmsActiveMQApplication.class, args);

        System.out.println("# Beans: " + ctx.getBeanDefinitionCount());

        var names = ctx.getBeanDefinitionNames();
        for (var name : names) {
            var bean = ctx.getBean(name);
            System.out.printf(MSG, name, bean.getClass().getSimpleName());
        }
    }

  @Bean
  public ConnectionFactory connectionFactory() {
      var connectionFactory =
              new ActiveMQConnectionFactory("vm://localhost?broker.persistent=false");
      connectionFactory.setClientID("someId");
      connectionFactory.setCloseTimeout(125);
      return connectionFactory;
    }
}
