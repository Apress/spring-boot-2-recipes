package com.apress.springbootrecipes.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * Simple application which will print the number of beans in the context and
 * a sorted list of bean names.
 *
 * @author Marten Deinum
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class DemoApplication {

  public static void main(String[] args) {
    var ctx = SpringApplication.run(DemoApplication.class, args);

    System.out.println("# Beans: " + ctx.getBeanDefinitionCount());

    var names = ctx.getBeanDefinitionNames();
    Arrays.sort(names);
    Arrays.asList(names).forEach(System.out::println);
  }
}
