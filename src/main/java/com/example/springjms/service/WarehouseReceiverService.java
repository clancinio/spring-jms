package com.example.springjms.service;

import com.example.springjms.model.BookOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WarehouseReceiverService {

  @Autowired
  private WarehouseProcessingService warehouseProcessingService;

  @JmsListener(destination = "${jms.template.book-order-queue}")
  public void receive(BookOrder bookOrder) {
    log.info("Message received!");
    log.info("Message is: {}", bookOrder);
    warehouseProcessingService.processOrder(bookOrder);
  }
}
