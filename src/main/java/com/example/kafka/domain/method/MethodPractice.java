package com.example.kafka.domain.method;

import java.util.Objects;

public class MethodPractice {
  // effective java method
  private String strategy;

  public static void main(String[] args) {
    MethodPractice methodPractice = new MethodPractice();
    methodPractice.test();
  }

  public void test() {
//    this.strategy = "전략";
    this.strategy = Objects.requireNonNull(strategy, "");
    System.out.println(strategy);
  }
}
