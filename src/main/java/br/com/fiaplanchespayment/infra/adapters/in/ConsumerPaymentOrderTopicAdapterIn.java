package br.com.fiaplanchespayment.infra.adapters.in;

import br.com.fiaplanchespayment.application.dtos.PaymentOrderDto;
import br.com.fiaplanchespayment.application.ports.in.PaymentOrderPortIn;
import br.com.fiaplanchespayment.domain.PaymentOrder;
import br.com.fiaplanchespayment.infra.mapper.PaymentMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ConsumerPaymentOrderTopicAdapterIn {

    private final PaymentOrderPortIn paymentOrderPortIn;

    private final PaymentMapper paymentMapper;

    public ConsumerPaymentOrderTopicAdapterIn(PaymentOrderPortIn paymentOrderPortIn, PaymentMapper paymentMapper) {
        this.paymentOrderPortIn = paymentOrderPortIn;
        this.paymentMapper = paymentMapper;
    }

    @KafkaListener(topics = "fiap-lanches-payment", groupId = "fiap-lanches-payment", containerFactory = "kafkaListenerContainerFactoryPaymentOrder")
    private void subscriber(ConsumerRecord<String, PaymentOrder> paymentOrder) {
        PaymentOrderDto paymentOrderDto = paymentMapper.paymentOrderToPaymentDto(paymentOrder.value());
        paymentOrderPortIn.executePaymentOrder(paymentOrderDto);
    }
}
