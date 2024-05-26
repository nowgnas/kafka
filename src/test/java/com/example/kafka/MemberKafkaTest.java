package com.example.kafka;

import com.example.kafka.domain.v1.member.model.MemberInformation;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootTest
public class MemberKafkaTest {
  @Autowired private KafkaTemplate<String, String> kafkaTemplate;

  @Test
  void kafkaProduceConsumerTest() {
    MemberInformation memberInformation =
        MemberInformation.builder().name("NAME").id("owijfow").role("customer").CD("10").build();

    kafkaTemplate.send("member-topic", memberInformation.toString());
  }
}
