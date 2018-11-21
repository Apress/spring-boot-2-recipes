package com.apress.springbootrecipes.echo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EchoHandlerIntegrationTest {

  @LocalServerPort
  private int port;

  private WebSocketStompClient stompClient;

  private List<StompSession> sessions = new ArrayList<>();

  @Before
  public void setup() {
    var webSocketClient = new StandardWebSocketClient();
    stompClient = new WebSocketStompClient(webSocketClient);
  }

  @After
  public void cleanUp() {
    this.sessions.forEach(StompSession::disconnect);
    this.sessions.clear();
  }

  @Test
  public void shouldSendAndReceiveMessage() throws Exception {
    CompletableFuture<String> answer = new CompletableFuture<>();
    var stompSession = connectAndSubscribe(answer);

    stompSession.send("/app/echo", "Hello World!".getBytes());

    var result = answer.get(1, TimeUnit.SECONDS);
    assertThat(result).isEqualTo("RECEIVED: Hello World!");

  }

  private StompSession connectAndSubscribe(CompletableFuture<String> answer)
          throws InterruptedException, ExecutionException, TimeoutException {
    var uri = "ws://localhost:" + port + "/echo-endpoint";
    var stompSession =
      stompClient.connect(uri, new StompSessionHandlerAdapter() {})
        .get(1, TimeUnit.SECONDS);

    stompSession.subscribe("/topic/echo",
            new TestStompFrameHandler(answer));
    this.sessions.add(stompSession);
    return stompSession;
  }

  private static class TestStompFrameHandler
          extends StompSessionHandlerAdapter {

    private final CompletableFuture<String> answer;

    private TestStompFrameHandler(CompletableFuture<String> answer) {
      this.answer = answer;
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
      return byte[].class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
      answer.complete(new String((byte[]) payload));
    }
  }
}
