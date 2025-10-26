package io.github.mrspock182.kafka_consumer2.consumer;

import io.github.mrspock182.kafka_consumer2.entity.Coxinha;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.TopicSuffixingStrategy;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    @RetryableTopic(
            autoCreateTopics = "false",
            backoff = @Backoff(
                    delay = 15000,
                    multiplier = 2.0,
                    maxDelay = 54000),
            topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE
    )
    @KafkaListener(
            topics = "${spring.kafka.topic.coxinha}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerFactory")
    public void listener(@Payload ConsumerRecord<String, Coxinha> consumerRecord) {
        System.out.println("KEY: " + consumerRecord.key());
        System.out.println("VALUE: " + consumerRecord.value());
    }
}