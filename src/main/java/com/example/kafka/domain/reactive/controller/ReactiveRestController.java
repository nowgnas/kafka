package com.example.kafka.domain.reactive.controller;

import com.example.kafka.config.reactive.kafka.ReactiveKafkaProducer;
import com.example.kafka.domain.reactive.model.value.MemberInformation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReactiveRestController {
  private final ReactiveKafkaProducer reactiveKafkaProducer;
  private final ObjectMapper objectMapper;

  @GetMapping("/reactive/produce")
  public String produce() throws JsonProcessingException {
    MemberInformation member = MemberInformation.getValue("001", "lee");
    reactiveKafkaProducer
        .sendMessage("reactive-kafka-topic", "key1", objectMapper.writeValueAsString(member))
        .subscribe();
    return null;
  }
}
