package com.example.kafka.config.confluent.consumer;

public class KafkaConfigurationValues {
	public static final String SASL_JASS_CONFIG = "sasl.jaas.config";
	public static final String SASL_MECHANISM = "sasl.mechanism";
	public static final String SECURITY_PROTOCOL = "security.protocol";
	public static final String LATEST = "latest";
	public static final String RETRY_COUNT_HEADER = "retry-count";
	public static final Integer RETRY_DEFAULT_COUNT = 1;
	public static final String ISOLATION_LEVEL = "read_committed";
	public static final Integer CONCURRENCY = 1;

	private KafkaConfigurationValues() {
		throw new IllegalStateException("Kafka util class");
	}
}
