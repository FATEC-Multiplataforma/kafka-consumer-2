package io.github.mrspock182.kafka_consumer2.configuration;

import io.github.mrspock182.kafka_consumer2.entity.Coxinha;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.mapping.DefaultJackson2JavaTypeMapper;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
public class KafkaListenerConfig {
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Coxinha> kafkaListenerFactory(
            final KafkaProperties kafkaProperties) {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, Coxinha>();
        factory.setConsumerFactory(consumerFactory(kafkaProperties));
        return factory;
    }

    private ConsumerFactory<String, Coxinha> consumerFactory(final KafkaProperties kafkaProperties) {
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        typeMapper.addTrustedPackages("*");
        JsonDeserializer<Coxinha> valueDeserializer = new JsonDeserializer<>(Coxinha.class);
        valueDeserializer.setTypeMapper(typeMapper);
        valueDeserializer.setUseTypeMapperForKey(true);
        return new DefaultKafkaConsumerFactory<>(
                kafkaProperties.buildConsumerProperties(),
                new StringDeserializer(),
                valueDeserializer);
    }
}
