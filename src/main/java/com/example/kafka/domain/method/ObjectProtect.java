package com.example.kafka.domain.method;

import lombok.Getter;

@Getter
public class ObjectProtect {
  private String start;
  private String end;

  public ObjectProtect(ObjectProtect start, ObjectProtect end) {
    this.start = start.getStart();
    this.end = end.getEnd();

    if (this.start.compareTo(this.end) > 0) {
      throw new IllegalArgumentException();
    }
  }
}
