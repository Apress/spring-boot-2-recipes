package com.apress.springbootrecipes.demo;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Marten Deinum
 */
@SpringBootApplication
@EnableScheduling
public class RabbitSenderApplication {

    public static void main(String[] args) {
			SpringApplication.run(RabbitSenderApplication.class, args);
    }
}


@Component
class HelloWorldSender {


	private final RabbitTemplate rabbit;

	HelloWorldSender(RabbitTemplate rabbit) {
		this.rabbit = rabbit;
	}

	@Scheduled(fixedRate = 500)
	public void sendTime() {

		rabbit.convertAndSend( "hello", "Hello World, from Spring Boot 2, over RabbitMQ!");
	}
}

@Component
class HelloWorldReceiver {

	@RabbitListener( queues = "hello")
	public void receive(String msg) {
		System.out.println("Received: " + msg);
	}

}
