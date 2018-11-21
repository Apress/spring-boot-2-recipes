package com.apress.springbootrecipes.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.stream.Stream;

/**
 * @author Marten Deinum
 */
@SpringBootApplication
public class JmsJndiApplication {

	private static final String MSG = "\tName: %100s, Type: %s\n";

	public static void main(String[] args) {

			SpringApplication.run(JmsJndiApplication.class, args);
			var ctx =
							SpringApplication.run(JmsJndiApplication.class, args);

			System.out.println("# Beans: " + ctx.getBeanDefinitionCount());

			var names = ctx.getBeanDefinitionNames();
			Stream.of(names)
							.filter(name -> name.toLowerCase().contains("jms") || ctx.getType(name).getName().contains("jms"))
							.forEach(name -> {
								var bean = ctx.getBean(name);
								System.out.printf(MSG, name, bean.getClass().getSimpleName());
							});
		}
}

@Component
class SimpleJmsListener {

	@JmsListener(destination = "current-time")
	public void onMessage(LocalDateTime serverDateTime) {
		System.out.println("Current Date & Time: " + serverDateTime);
	}

}
