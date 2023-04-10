package com.example.springjms.service;

import com.example.springjms.model.BookOrder;
import com.example.springjms.model.ProcessedBookOrder;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WarehouseProcessingService {

  @Autowired
  private JmsTemplate jmsTemplate;

  public void processOrder(BookOrder bookOrder) {
    log.info("Processing book order...");
    ProcessedBookOrder processedBookOrder = ProcessedBookOrder.builder()
        .bookOrder(bookOrder)
        .processingDateTime(LocalDateTime.now())
        .expectedShippingDateTime(LocalDateTime.now().plusDays(1))
        .build();
    log.info("Processing book order: {}", processedBookOrder);
    jmsTemplate.convertAndSend("book.order.processed.queue", processedBookOrder);
  }

}
