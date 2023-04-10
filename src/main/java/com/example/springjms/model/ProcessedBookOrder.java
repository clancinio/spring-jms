package com.example.springjms.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProcessedBookOrder {

  private BookOrder bookOrder;
  private LocalDateTime processingDateTime;
  private LocalDateTime expectedShippingDateTime;

  @JsonCreator
  public ProcessedBookOrder(
      @JsonProperty("bookOrder") BookOrder bookOrder,
      @JsonProperty("processingDateTime") LocalDateTime processingDateTime,
      @JsonProperty("expectedShippingDateTime") LocalDateTime expectedShippingDateTime) {
    this.bookOrder = bookOrder;
    this.processingDateTime = processingDateTime;
    this.expectedShippingDateTime = expectedShippingDateTime;
  }
}
