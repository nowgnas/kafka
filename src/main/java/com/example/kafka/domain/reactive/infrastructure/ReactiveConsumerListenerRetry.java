package com.example.kafka.domain.reactive.infrastructure;

import com.example.kafka.config.reactive.kafka.ReactiveKafkaConsumer;
import com.example.kafka.domain.reactive.model.value.MemberInformation;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.time.Duration;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReactiveConsumerListenerRetry {
  private final ReactiveKafkaConsumer reactiveKafkaConsumer;
  private final ObjectMapper objectMapper;

  @PostConstruct
  public void reactiveKafkaConsumerListener() {
    reactiveKafkaConsumer
        .receiver("reactive-kafka-topic")
        .flatMap(
            receiverRecord -> {
              return Mono.fromCallable(
                      () -> {
                        String message = receiverRecord.value();
                        MemberInformation memberInformation;

                        try {
                          // Deserialize message
                          memberInformation =
                              objectMapper.readValue(message, MemberInformation.class);

                          // Simulate validation failure
                          if (!"002".equals(memberInformation.getMemberNo())) {
                            throw new IllegalArgumentException("Wrong memberNo");
                          }

                        } catch (IOException e) {
                          // If deserialization fails, propagate error
                          log.error("Failed to deserialize message: {}", message, e);
                          throw new RuntimeException(e);
                        }

                        return receiverRecord; // Return processed record
                      })
                  .doOnSuccess(
                      record -> {
                        log.info("Message processed successfully: {}", record.value());
                        record.receiverOffset().acknowledge(); // Acknowledge offset if successful
                      })
                  .doOnError(
                      error -> {
                        log.error("Processing failed: {}", error.getMessage());
                        // Error is automatically retried by retryWhen
                      });
            })
        .retryWhen(
            errors ->
                errors
                    .zipWith(Flux.range(1, 3), (error, attempt) -> attempt)
                    .flatMap(
                        attempt -> {
                          log.warn("Retrying attempt #{}", attempt);
                          return Mono.delay(Duration.ofSeconds(1)); // Add delay between retries
                        }))
        .subscribe(
            success -> log.info("Message successfully processed and committed."),
            error ->
                log.error(
                    "All retries failed, message will not be reprocessed: {}", error.getMessage()));
  }
}
