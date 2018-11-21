package com.apress.springbootrecipes.echo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EchoHandlerIntegrationTest {

  @LocalServerPort
  private int port;

  @Test
  public void shouldProperlyCloseConnection() throws Exception {
    var container = ContainerProvider.getWebSocketContainer();
    var uri = URI.create("ws://localhost:" + port + "/echo");
    var testClient = new SimpleTestClientEndpoint();
    container.connectToServer(testClient, uri);

    testClient.closeAndWait(15);

    assertThat(testClient.isClosed()).isTrue();
    assertThat(testClient.getCloseReason())
            .hasFieldOrPropertyWithValue("closeCode", CloseReason.CloseCodes.NORMAL_CLOSURE);

  }

  @Test
  public void sendAndReceiveMessage() throws Exception {
    var container = ContainerProvider.getWebSocketContainer();
    var uri = URI.create("ws://localhost:" + port + "/echo");
    var testClient = new SimpleTestClientEndpoint();
    container.connectToServer(testClient, uri);

    testClient.sendTextAndWait("Hello World!", 200);
    testClient.closeAndWait(2);

    assertThat(testClient.getReceived())
            .containsExactly("RECEIVED: Hello World!");


  }

  @Configuration
  @Import(EchoApplication.class)
  public static class EchoHandlerIntegrationTestConfiguratation {

    /**
     * Explicitly start Netty. Else while testing a Tomcat instance would
     * be started.
     *
     * @return
     */
    @Bean
    public NettyReactiveWebServerFactory webServerFactory() {
      return new NettyReactiveWebServerFactory();
    }
  }

  @ClientEndpoint
  public static class SimpleTestClientEndpoint {

    private volatile List<String> received = new ArrayList<>();
    private volatile Session session;
    private volatile CloseReason closeReason;
    private volatile boolean closed = false;

    @OnOpen
    public void onOpen(Session session) {
      this.session = session;
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
      this.closeReason = reason;
      this.closed = true;
    }

    @OnMessage
    public void onMessage(String message) {
      this.received.add(message);
    }

    public void sendTextAndWait(String text, long timeout)
            throws IOException, InterruptedException {
      var current = received.size();
      session.getBasicRemote().sendText(text);
      wait(() -> received.size() == current, timeout);
    }

    public void closeAndWait(long timeout) throws IOException, InterruptedException {
      if (session != null && !closed) {
        session.close();
      }
      wait(() -> closeReason == null, timeout);
    }

    private void wait(Supplier<Boolean> condition, long timeout)
            throws InterruptedException {
      var waited = 0;
      while (condition.get() && waited < timeout) {
        Thread.sleep(1);
        waited += 1;
      }
    }

    public CloseReason getCloseReason() {
      return closeReason;
    }

    public List<String> getReceived() {
      return this.received;
    }

    public boolean isClosed() {
      return closed;
    }
  }
}
