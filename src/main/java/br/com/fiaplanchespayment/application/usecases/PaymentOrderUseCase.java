package br.com.fiaplanchespayment.application.usecases;

import br.com.fiaplanchespayment.application.dtos.PaymentDto;
import br.com.fiaplanchespayment.application.dtos.PaymentOrderDto;
import br.com.fiaplanchespayment.application.dtos.UpdatePaymentOrderDto;
import br.com.fiaplanchespayment.application.ports.in.PaymentOrderPortIn;
import br.com.fiaplanchespayment.application.ports.out.NotifyPaymentTopicPortOut;
import br.com.fiaplanchespayment.application.ports.out.PaymentRepositoryPortOut;
import br.com.fiaplanchespayment.domain.PaymentValidatorPort;
import br.com.fiaplanchespayment.domain.enums.OrderStatus;
import lombok.Generated;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Generated
@Slf4j
public class PaymentOrderUseCase implements PaymentOrderPortIn {

    private final PaymentRepositoryPortOut paymentRepositoryPortOut;
    private final NotifyPaymentTopicPortOut notifyPaymentTopicPortOut;
    private final PaymentValidatorPort paymentValidatorPort;

    public PaymentOrderUseCase(PaymentRepositoryPortOut paymentRepositoryPortOut, NotifyPaymentTopicPortOut notifyPaymentTopicPortOut, PaymentValidatorPort paymentValidatorPort) {
        this.paymentRepositoryPortOut = paymentRepositoryPortOut;
        this.notifyPaymentTopicPortOut = notifyPaymentTopicPortOut;
        this.paymentValidatorPort = paymentValidatorPort;
    }

    @SneakyThrows
    @Override
    public void executePaymentOrder(PaymentOrderDto paymentOrderDto) {
        log.info("Executando o pagamento do pedido: {}", paymentOrderDto.orderId());
        if (!paymentValidatorPort.validatePaymentOrder(paymentOrderDto)) {
            return;
        }
        PaymentDto paymentDto = new PaymentDto(
                paymentOrderDto.orderId(),
                OrderStatus.PAGAMENTO_APROVADO,
                paymentOrderDto.paymentValue(),
                paymentOrderDto.paymentMethod(),
                LocalDateTime.now()
        );

        PaymentDto paymentDtoSaved = paymentRepositoryPortOut.save(paymentDto);

        UpdatePaymentOrderDto updatePaymentOrderDto = new UpdatePaymentOrderDto(
                paymentDtoSaved.orderId(),
                paymentDtoSaved.status()
        );
        notifyPaymentTopicPortOut.notifyPaymentOrder(updatePaymentOrderDto);
        log.info("Pagamento do pedido executado com sucesso");
    }
}
