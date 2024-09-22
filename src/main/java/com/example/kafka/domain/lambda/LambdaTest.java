package com.example.kafka.domain.lambda;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LambdaTest {
  public void lambda() {
    List<String> words = new ArrayList<>();
    words.sort(Comparator.comparingInt(String::length));
  }
}
