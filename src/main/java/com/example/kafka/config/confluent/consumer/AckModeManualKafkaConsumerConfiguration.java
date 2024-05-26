package com.example.kafka.config.confluent.consumer;

import static com.example.kafka.config.confluent.consumer.KafkaConfigurationValues.CONCURRENCY;
import static com.example.kafka.config.confluent.consumer.KafkaConfigurationValues.ISOLATION_LEVEL;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Slf4j
@EnableKafka
@Configuration
@RequiredArgsConstructor
@ConditionalOnExpression("'${confluent.consumer.bootstrapServers:}' != ''")
public class AckModeManualKafkaConsumerConfiguration {

  @Value("${confluent.consumer.bootstrapServers}")
  private String bootstrapServers;

  @Primary
  @Bean("ackModeManualKafkaConsumerFactory")
  public ConsumerFactory<String, String> ackModeManualConsumerFactory() {
    return new DefaultKafkaConsumerFactory<>(ackModeManualConsumerConfig());
  }

  @Bean("ackModeManualKafkaContainerFactory")
  public ConcurrentKafkaListenerContainerFactory<String, String>
      ackModeManualKafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, String> container =
        new ConcurrentKafkaListenerContainerFactory<>();
    container.setConsumerFactory(ackModeManualConsumerFactory());
    container.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
    container.setConcurrency(CONCURRENCY);
    container.setRetryTemplate(retryTemplate());
    return container;
  }

  @Bean
  public Map<String, Object> ackModeManualConsumerConfig() {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, 1000);
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, KafkaConfigurationValues.LATEST);
    props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);


    return props;
  }

  @Bean
  public RetryTemplate retryTemplate() {
    RetryTemplate retryTemplate = new RetryTemplate();

    FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
    backOffPolicy.setBackOffPeriod(5000);
    retryTemplate.setBackOffPolicy(backOffPolicy);

    SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
    retryPolicy.setMaxAttempts(3);
    retryTemplate.setRetryPolicy(retryPolicy);

    return retryTemplate;
  }
}
