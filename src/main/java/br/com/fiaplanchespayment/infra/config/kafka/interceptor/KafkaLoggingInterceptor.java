package br.com.fiaplanchespayment.infra.config.kafka.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerInterceptor;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.MDC;

import java.util.Map;

@Slf4j
public class KafkaLoggingInterceptor implements ConsumerInterceptor<String, String> {
    @Override
    public ConsumerRecords<String, String> onConsume(ConsumerRecords<String, String> consumerRecords) {
        log.info("START CONSUME KAFKA");
        consumerRecords.forEach(
                consumerRecord -> {
                    log.info(
                            "TOPIC: {}, KEY: {}, VALUE: {}",
                            consumerRecord.topic(),
                            consumerRecord.key(),
                            consumerRecord.value()
                    );
                    MDC.put("UUID", consumerRecord.key());
                }
        );

        return consumerRecords;
    }

    @Override
    public void onCommit(Map<TopicPartition, OffsetAndMetadata> map) {

    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
