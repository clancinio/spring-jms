package com.example.springjms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringJmsApplication {

  public static void main(String[] args) {

    SpringApplication.run(SpringJmsApplication.class, args);
//    ConfigurableApplicationContext context = SpringApplication.run(SpringJmsApplication.class,
//        args);
//
//    JmsListenerContainerFactory jmsListenerContainerFactory = context.getBean(
//        JmsListenerContainerFactory.class);
//		MessageSender sender =  context.getBean(MessageSender.class);
//
//		sender.sendMessage("order-queue", "Hello JMS!");
  }

}
