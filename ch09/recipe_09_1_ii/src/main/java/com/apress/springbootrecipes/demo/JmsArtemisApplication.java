package com.apress.springbootrecipes.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Comparator;
import java.util.stream.Stream;

/**
 * @author Marten Deinum
 */
@SpringBootApplication
public class JmsArtemisApplication {

    private static final String MSG = "\tName: %100s, Type: %s\n";

    public static void main(String[] args) {
			var ctx =
							SpringApplication.run(JmsArtemisApplication.class, args);

			System.out.println("# Beans: " + ctx.getBeanDefinitionCount());


			var names = ctx.getBeanDefinitionNames();
			Stream.of(names)
							.filter(name -> name.toLowerCase().contains("jms") || ctx.getType(name).getName().contains("jms"))
              .sorted(Comparator.naturalOrder())
							.forEach(name -> {
								var bean = ctx.getBean(name);
								System.out.printf(MSG, name, bean.getClass().getSimpleName());
							});
		}
}
