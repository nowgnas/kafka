package com.example.kafka.domain.reactive.infrastructure;

import com.example.kafka.config.reactive.kafka.ReactiveKafkaConsumer;
import com.example.kafka.domain.reactive.model.value.MemberInformation;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReactiveConsumerListener {
  private final ReactiveKafkaConsumer reactiveKafkaConsumer;
  private final ObjectMapper objectMapper;

  @PostConstruct
  public void reactiveKafkaConsumerListener() {
    reactiveKafkaConsumer
        .receiver("reactive-kafka-topic")
        .doOnError(
            error -> {
              log.error("receiver error");
            })
        .subscribe(
            success -> {
              MemberInformation memberInformation = null;
              try {
                memberInformation =
                    objectMapper.readValue(success.value(), MemberInformation.class);
              } catch (IOException e) {
                throw new RuntimeException(e);
              }
              log.info("consumer message: {}", memberInformation.toString());
              log.info("Message successfully processed and committed.");
              success.receiverOffset().acknowledge();
            },
            error ->
                log.error(
                    "All retries failed, message will not be reprocessed: {}", error.getMessage()));
  }
}
