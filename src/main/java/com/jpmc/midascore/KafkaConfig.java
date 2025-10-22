package com.jpmc.midascore;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;

@Configuration
public class KafkaConfig {

    // Use String, Object generics for maximum compatibility when dealing with JSON
    private final ConsumerFactory<String, Object> consumerFactory;

    // Spring Boot auto-wires the default ConsumerFactory
    public KafkaConfig(ConsumerFactory<String, Object> consumerFactory) {
        this.consumerFactory = consumerFactory;
    }

    /**
     * Defines the Kafka Listener Factory. 
     * We rely on the configured JSON deserializer in application.yml for conversion.
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = 
            new ConcurrentKafkaListenerContainerFactory<>();
        
        // This is the essential step for Spring to know where to get messages
        factory.setConsumerFactory(this.consumerFactory);
        
        // **REMOVE: factory.setMessageConverter(new StringJsonMessageConverter());**
        // The setMessageConverter method is not present in the factory for Spring Boot 3.x.
        // The consumer deserializer config in application.yml handles the JSON conversion.
        
        return factory;
    }
}