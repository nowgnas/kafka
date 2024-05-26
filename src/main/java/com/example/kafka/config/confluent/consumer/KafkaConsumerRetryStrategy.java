package com.example.kafka.config.confluent.consumer;

import static com.example.kafka.config.confluent.consumer.KafkaConfigurationValues.RETRY_COUNT_HEADER;
import static com.example.kafka.config.confluent.consumer.KafkaConfigurationValues.RETRY_DEFAULT_COUNT;

import com.example.kafka.domain.v1.member.exception.DeliveryInformationConsumerException;
import java.util.Optional;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaConsumerRetryStrategy {
  public void consumerRetryStrategy(
      ConsumerRecord<String, String> consumerRecord, Acknowledgment acknowledgment, Exception e) {
    addRetryCount(consumerRecord);
    if (shouldRetry(consumerRecord)) {
      throw new DeliveryInformationConsumerException(e.getMessage());
    } else {
      handleFailedRecord(consumerRecord, e);
      acknowledgment.acknowledge();
    }
  }

  private void addRetryCount(ConsumerRecord<String, String> consumerRecord) {
    Optional<Header> header =
        Optional.ofNullable(consumerRecord.headers().lastHeader(RETRY_COUNT_HEADER));
    if (!header.isPresent()) {
      consumerRecord
          .headers()
          .add(RETRY_COUNT_HEADER, Integer.toString(RETRY_DEFAULT_COUNT).getBytes());
    } else {
      int retryCount = Integer.parseInt(new String(header.get().value()));
      consumerRecord.headers().remove(RETRY_COUNT_HEADER);
      consumerRecord.headers().add(RETRY_COUNT_HEADER, Integer.toString(retryCount + 1).getBytes());
    }
  }

  private void handleFailedRecord(ConsumerRecord<String, String> consumerRecord, Exception e) {
    log.error("[Handle Failed Record] : {}", consumerRecord.topic());
    log.error(
        "[KAFKA LISTENER ERROR] :: TOPIC > {} | MESSAGE > {}, message: {}",
        consumerRecord.topic(),
        consumerRecord.value(),
        e.getMessage());
  }

  private boolean shouldRetry(ConsumerRecord<String, String> consumerRecord) {
    int retryValue =
        Integer.parseInt(
            new String(consumerRecord.headers().lastHeader(RETRY_COUNT_HEADER).value()));
    log.info("retry count: {}", retryValue);
    return retryValue < 3;
  }
}
