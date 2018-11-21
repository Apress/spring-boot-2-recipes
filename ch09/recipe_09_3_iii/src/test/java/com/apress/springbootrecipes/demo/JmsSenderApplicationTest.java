package com.apress.springbootrecipes.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.jms.BytesMessage;
import javax.jms.Message;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JmsSenderApplicationTest {

	@Autowired
	private JmsTemplate jms;

	@Test
	public void shouldReceiveOrderPlain() throws Exception {

		Message message = jms.receive("orders");

		assertThat(message)
						.isInstanceOf(BytesMessage.class);

		BytesMessage msg = (BytesMessage) message;

		ObjectMapper mapper = new ObjectMapper();
		byte[] content = new byte[(int) msg.getBodyLength()];
		msg.readBytes(content);
		Order order = mapper.readValue( content, Order.class);

		System.out.println(order);

		assertThat(order).hasNoNullFieldsOrProperties();
	}

	@Test
	public void shouldReceiveOrderWithConversion() throws Exception {

		Order order = (Order) jms.receiveAndConvert("orders");
		System.out.println(order);

		assertThat(order).hasNoNullFieldsOrProperties();
	}
}
