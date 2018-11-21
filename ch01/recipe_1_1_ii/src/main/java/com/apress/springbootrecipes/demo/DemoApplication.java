package com.apress.springbootrecipes.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

/**
 * @author Marten Deinum
 */
@SpringBootApplication
public class DemoApplication {

  public static void main(String[] args) {
    var ctx = SpringApplication.run(DemoApplication.class, args);

    System.out.println("# Beans: " + ctx.getBeanDefinitionCount());

    var names = ctx.getBeanDefinitionNames();
    Arrays.sort(names);
    Arrays.asList(names).forEach(System.out::println);
  }
}
