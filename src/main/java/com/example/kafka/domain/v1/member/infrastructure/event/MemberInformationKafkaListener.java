package com.example.kafka.domain.v1.member.infrastructure.event;

import com.example.kafka.config.confluent.annotation.AckModeManualKafkaListener;
import com.example.kafka.config.confluent.consumer.KafkaConsumerRetryStrategy;
import com.example.kafka.domain.v1.member.model.MemberInformation;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberInformationKafkaListener
    implements AcknowledgingMessageListener<String, String> {
  private final KafkaConsumerRetryStrategy kafkaConsumerRetryStrategy;
  private final ObjectMapper objectMapper;

  @Override
  @AckModeManualKafkaListener(topics = "member-topic", groupId = "${confluent.consumer.groupId}")
  public void onMessage(
      ConsumerRecord<String, String> consumerRecord, Acknowledgment acknowledgment) {
    try {
      log.info(consumerRecord.toString());
      MemberInformation memberInformation =
          objectMapper.readValue(consumerRecord.value(), MemberInformation.class);

      log.info(memberInformation.toString());
      acknowledgment.acknowledge();
    } catch (Exception e) {
      kafkaConsumerRetryStrategy.consumerRetryStrategy(consumerRecord, acknowledgment, e);
    }
  }
}
