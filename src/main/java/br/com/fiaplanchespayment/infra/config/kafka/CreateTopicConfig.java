package br.com.fiaplanchespayment.infra.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class CreateTopicConfig {

    @Value("${kafka.topic.producer.name}")
    private String topicToOrder;

    @Value("${kafka.topic.consumer.name}")
    private String topicFromOrder;

    @Bean
    public NewTopic topicToOrder() {
        return TopicBuilder.name(topicToOrder)
                .build();
    }

    @Bean
    public NewTopic topicFromOrder() {
        return TopicBuilder.name(topicFromOrder)
                .build();
    }
}
