package com.apress.springbootrecipes;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HelloWorldControllerIntegrationTest {

  @Autowired
  private TestRestTemplate testRestTemplate;

  @Test
  public void testHelloWorldController() throws Exception {

    String result = testRestTemplate.getForObject("/", String.class);
    assertThat(result).isEqualTo("Hello World, from Spring Boot 2!");
  }
}
