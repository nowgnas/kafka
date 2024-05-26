package com.example.kafka.domain.v1.member.test.controller;

import com.example.kafka.domain.v1.member.model.MemberInformation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class KafkaProduceTest {
  private final KafkaTemplate<String, String> kafkaTemplate;

  @GetMapping("kafka-produce")
  public void produce() {
    MemberInformation memberInformation =
        MemberInformation.builder().name("name").role("role").CD("10").id("TESTID12121").build();
    log.info(memberInformation.toString());
    // Create a Message object with the key and value
    ProducerRecord<String, String> record =
        new ProducerRecord<>("member-topic", "your-key", memberInformation.toString());

    // Send the message
    kafkaTemplate.send(record);
  }

  @GetMapping("kafka-retry")
  public void produceRetry() {

    kafkaTemplate.send("member-topic", "member retry test");
  }
}
