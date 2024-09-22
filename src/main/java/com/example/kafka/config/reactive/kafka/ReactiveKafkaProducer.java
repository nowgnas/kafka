package com.example.kafka.config.reactive.kafka;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;
import reactor.kafka.sender.SenderRecord;

@Slf4j
@Service
public class ReactiveKafkaProducer {
  private final KafkaSender<String, String> sender;

  public ReactiveKafkaProducer() {
    Map<String, Object> props = new HashMap<>();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

    SenderOptions<String, String> senderOptions = SenderOptions.create(props);
    this.sender = KafkaSender.create(senderOptions);
  }

  public Mono<Void> sendMessage(String topic, String key, String message) {
    return sender
        .send(Flux.just(SenderRecord.create(topic, null, null, key, message, null)))
        .doOnError(
            error -> {
              log.error("produce error");
              log.error(error.toString());
              error.printStackTrace();
            })
        .doOnNext(
            result -> {
              log.info("produce success");
            })
        .then();
  }
}
