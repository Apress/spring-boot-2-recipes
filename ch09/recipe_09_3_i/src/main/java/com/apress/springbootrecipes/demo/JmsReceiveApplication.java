package com.apress.springbootrecipes.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.time.LocalDateTime;

/**
 * @author Marten Deinum
 */
@SpringBootApplication
@EnableScheduling
public class JmsReceiveApplication {

    public static void main(String[] args) {
			SpringApplication.run(JmsReceiveApplication.class, args);
    }

}

@Component
class CurrentDateTimeService {

	@JmsListener(destination = "time-queue")
	public void handle(Message msg) throws JMSException {
		Assert.state(msg instanceof TextMessage, "Can only handle TextMessage");
		System.out.println("[RECEIVED] - " + ((TextMessage) msg).getText());
	}
}

@Component
class MessageSender {

	private final JmsTemplate jms;

	MessageSender(JmsTemplate jms) {
		this.jms = jms;
	}

	@Scheduled(fixedRate = 1000)
	public void sendTime() {
		jms.convertAndSend("time-queue", "Current Date & Time is: " + LocalDateTime.now());
	}
}
