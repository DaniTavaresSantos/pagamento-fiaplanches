package br.com.fiaplanchespayment.infra.adapters.out;

import br.com.fiaplanchespayment.application.dtos.PaymentDto;
import br.com.fiaplanchespayment.application.ports.out.NotifyPaymentTopicPortOut;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotifyPaymentTopicAdapterOut implements NotifyPaymentTopicPortOut {

    private final KafkaTemplate<String, PaymentDto> kafkaTemplatePaymentToOrder;

    public NotifyPaymentTopicAdapterOut(KafkaTemplate<String, PaymentDto> kafkaTemplatePaymentToOrder) {
        this.kafkaTemplatePaymentToOrder = kafkaTemplatePaymentToOrder;
    }

    @Override
    public void notifyPaymentOrder(PaymentDto paymentDto) {
        log.info("Atualizando status da ordem para o tópico fiap-lanches-order: {}", paymentDto);
        kafkaTemplatePaymentToOrder.send("fiap-lanches-order", paymentDto);
        log.info("Status enviado com sucesso");
    }
}
