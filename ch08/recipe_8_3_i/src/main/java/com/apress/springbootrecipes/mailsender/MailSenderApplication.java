package com.apress.springbootrecipes.mailsender;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@SpringBootApplication
public class MailSenderApplication {

	public static void main(String[] args) {
		SpringApplication.run(MailSenderApplication.class, args);
	}

	@Bean
	public ApplicationRunner startupMailSender(JavaMailSender mailSender) {
		return (args) -> mailSender.send((msg) -> {
      var helper = new MimeMessageHelper(msg);
      helper.setTo("recipient@some.where");
      helper.setFrom("spring-boot-2-recipes@apress.com");
      helper.setSubject("Status message");
      helper.setText("All is well.");
    });
	}
}
