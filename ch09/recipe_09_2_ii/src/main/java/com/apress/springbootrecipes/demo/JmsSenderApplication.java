package com.apress.springbootrecipes.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Marten Deinum
 */
@SpringBootApplication
@EnableScheduling
public class JmsSenderApplication {

    public static void main(String[] args) {
			SpringApplication.run(JmsSenderApplication.class, args);
    }

    @Bean
		public MappingJackson2MessageConverter messageConverter() {
			MappingJackson2MessageConverter messageConverter =
							new MappingJackson2MessageConverter();
			messageConverter.setTypeIdPropertyName("content-type");
			messageConverter.setTypeIdMappings(
							Collections.singletonMap("order", Order.class));

			return messageConverter;
		}
}


@Component
class OrderSender {


	private final JmsTemplate jms;

	OrderSender(JmsTemplate jms) {
		this.jms = jms;
	}

	@Scheduled(fixedRate = 500)
	public void sendTime() {

		var id = UUID.randomUUID().toString();
		var amount = ThreadLocalRandom.current().nextDouble(1000.00d);
		var order = new Order(id, BigDecimal.valueOf(amount));
		jms.convertAndSend("orders", order);
	}

}
