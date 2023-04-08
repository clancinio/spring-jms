package com.example.springjms.model;

import lombok.Data;

@Data
public class BookOrder {

  private String bookOrderId;
  private String bookId;
  private String customerId;
}
