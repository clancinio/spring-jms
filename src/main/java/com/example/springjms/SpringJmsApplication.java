package com.example.springjms;

import com.example.springjms.messaging.jms.MessageSender;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

@EnableJms
@SpringBootApplication
public class SpringJmsApplication {

	public static void main(String[] args) {

		SpringApplication.run(SpringJmsApplication.class, args);
		ConfigurableApplicationContext context = SpringApplication.run(SpringJmsApplication.class, args);
		MessageSender sender =  context.getBean(MessageSender.class);

		sender.sendMessage("order-queue", "Hello JMS!");
	}

}
