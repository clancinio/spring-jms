package com.example.springjms.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BookOrderProcessingMessageListener implements MessageListener {

  @Override
  public void onMessage(Message message) {

    try {
      String text = ((TextMessage) message).getText();
      log.info("Received Process Order from warehouse: {}", text);
    } catch (JMSException e) {
      e.printStackTrace();
    }
  }

}
