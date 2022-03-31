package pt.teixeiram2.tracing.config;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FizzGeneratorConfig {

    @Bean
    public Producer<Long, String> kafkaProducer(KafkaProperties kafkaProperties) {
        return new KafkaProducer<>(kafkaProperties.getProperties());
    }
}
