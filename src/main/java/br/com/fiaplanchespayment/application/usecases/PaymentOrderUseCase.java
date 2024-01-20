package br.com.fiaplanchespayment.application.usecases;

import br.com.fiaplanchespayment.application.dtos.PaymentDto;
import br.com.fiaplanchespayment.application.dtos.PaymentOrderDto;
import br.com.fiaplanchespayment.application.ports.in.PaymentOrderPortIn;
import br.com.fiaplanchespayment.application.ports.out.NotifyPaymentTopicPortOut;
import br.com.fiaplanchespayment.application.ports.out.PaymentRepositoryPortOut;
import br.com.fiaplanchespayment.domain.enums.OrderStatus;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
public class PaymentOrderUseCase implements PaymentOrderPortIn {

    private final PaymentRepositoryPortOut paymentRepositoryPortOut;

    private final NotifyPaymentTopicPortOut notifyPaymentTopicPortOut;

    public PaymentOrderUseCase(PaymentRepositoryPortOut paymentRepositoryPortOut, NotifyPaymentTopicPortOut notifyPaymentTopicPortOut) {
        this.paymentRepositoryPortOut = paymentRepositoryPortOut;
        this.notifyPaymentTopicPortOut = notifyPaymentTopicPortOut;
    }

    @Override
    public void executePaymentOrder(PaymentOrderDto paymentOrderDto) {
        log.info("Executando o pagamento do pedido: {}", paymentOrderDto.orderId());

        PaymentDto paymentDto = new PaymentDto(
                paymentOrderDto.orderId(),
                OrderStatus.PAGAMENTO_APROVADO,
                paymentOrderDto.paymentValue(),
                paymentOrderDto.paymentMethod(),
                LocalDateTime.now()
        );

        PaymentDto paymentDtoSaved = paymentRepositoryPortOut.save(paymentDto);
        notifyPaymentTopicPortOut.notifyPaymentOrder(paymentDtoSaved);
        log.info("Pagamento do pedido executado com sucesso");
    }
}
