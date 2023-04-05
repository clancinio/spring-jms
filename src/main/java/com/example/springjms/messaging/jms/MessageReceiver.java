package com.example.springjms.messaging.jms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MessageReceiver {

  @JmsListener(destination = "order-queue", containerFactory = "jmsListenerContainerFactory")
  public void receiveMessage(String message) {
    System.out.println("Order received: " + message);
  }

}
