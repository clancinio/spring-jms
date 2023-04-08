package com.example.springjms.service;

import com.example.springjms.model.BookOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class BookStoreOrderService {

  @Autowired
  JmsTemplate jmsTemplate;

  private static String BOOK_QUEUE = "book.order.queue";

  public void send(BookOrder bookOrder){
    jmsTemplate.convertAndSend(BOOK_QUEUE, bookOrder);
  }
}
