package com.example.springjms;

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
		JmsTemplate jmsTemplate =  context.getBean(JmsTemplate.class);

		jmsTemplate.convertAndSend("order-queue", "Hello JMS!");
	}

}
