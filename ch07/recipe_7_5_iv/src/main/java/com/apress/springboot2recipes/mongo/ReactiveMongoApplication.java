package com.apress.springboot2recipes.mongo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.time.Duration;

@SpringBootApplication
public class ReactiveMongoApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(ReactiveMongoApplication.class, args);
		System.in.read();
	}
}

@Component
@Order(1)
class DataInitializer implements ApplicationRunner {

	private final CustomerRepository customers;

	DataInitializer(CustomerRepository customers) {
		this.customers = customers;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

		customers.deleteAll().thenMany(
						Flux.just(
							new Customer("Marten Deinum", "marten.deinum@conspect.nl"),
							new Customer("Josh Long", "jlong@pivotal.io"),
							new Customer("John Doe", "john.doe@island.io"),
							new Customer("Jane Doe", "jane.doe@island.io"))
		).delayElements(Duration.ofMillis(250))
			.flatMap(customers::save)
			.subscribe(System.out::println);
	}
}

@Component
class CustomerLister implements ApplicationRunner {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final CustomerRepository customers;

	CustomerLister(CustomerRepository customers) {
		this.customers = customers;
	}

	@Override
	public void run(ApplicationArguments args) {

		customers.findAll().subscribe(customer -> logger.info("{}", customer));

	}
}
