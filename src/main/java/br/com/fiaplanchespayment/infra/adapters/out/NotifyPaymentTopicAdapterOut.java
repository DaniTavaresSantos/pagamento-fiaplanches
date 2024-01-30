package br.com.fiaplanchespayment.infra.adapters.out;

import br.com.fiaplanchespayment.application.dtos.PaymentDto;
import br.com.fiaplanchespayment.application.dtos.UpdatePaymentOrderDto;
import br.com.fiaplanchespayment.application.ports.out.NotifyPaymentTopicPortOut;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotifyPaymentTopicAdapterOut implements NotifyPaymentTopicPortOut {

    private final KafkaTemplate<String, UpdatePaymentOrderDto> kafkaTemplatePaymentToOrder;

    @Value("${kafka.topic.producer.name}")
    private String topicName;

    public NotifyPaymentTopicAdapterOut(KafkaTemplate<String, UpdatePaymentOrderDto> kafkaTemplatePaymentToOrder) {
        this.kafkaTemplatePaymentToOrder = kafkaTemplatePaymentToOrder;
    }

    @Override
    public void notifyPaymentOrder(UpdatePaymentOrderDto updatePaymentOrderDto) {
        log.info("Atualizando status da ordem para o tópico fiap-lanches-order: {}", updatePaymentOrderDto);
        kafkaTemplatePaymentToOrder.send(topicName, updatePaymentOrderDto);
        log.info("Status enviado com sucesso");
    }
}
