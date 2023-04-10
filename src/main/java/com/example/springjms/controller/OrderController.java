package com.example.springjms.controller;

import com.example.springjms.model.BookOrder;
import com.example.springjms.service.BookStoreOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/process/order")
public class OrderController {

  private final BookStoreOrderService bookStoreOrderService;

  @PostMapping()
  public ResponseEntity<String> processOrder(@RequestBody BookOrder bookOrder) {
    bookStoreOrderService.send(bookOrder);
    return ResponseEntity.status(HttpStatus.OK).body("Message sent successfully");
  }

}
