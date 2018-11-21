package com.apress.springbootrecipes.echo;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EchoHandlerTest {

  private final EchoHandler handler = new EchoHandler();

  @Test
  public void shouldEchoMessage() throws Exception {

    var msg = "Hello World!";
    assertThat(handler.echo(msg)).isEqualTo("RECEIVED: " + msg);


  }

}
