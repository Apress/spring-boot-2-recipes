package com.apress.springbootrecipes.mailsender;

import com.icegreen.greenmail.junit.GreenMailRule;
import com.icegreen.greenmail.util.ServerSetupTest;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.internet.MimeMessage;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailSenderApplicationTest {

	@ClassRule
	public static final GreenMailRule greenMail = new GreenMailRule(ServerSetupTest.ALL);

	@After
	public void cleanUp() {
		greenMail.reset();
	}

	@Test
	public void shouldHaveSendMail() throws Exception {
		MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
		assertThat(receivedMessages).hasSize(1);
	}
}
