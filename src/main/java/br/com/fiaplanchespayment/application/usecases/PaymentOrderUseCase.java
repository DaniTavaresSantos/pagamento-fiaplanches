package br.com.fiaplanchespayment.application.usecases;

import br.com.fiaplanchespayment.application.dtos.PaymentDto;
import br.com.fiaplanchespayment.application.dtos.PaymentOrderDto;
import br.com.fiaplanchespayment.application.dtos.UpdatePaymentOrderDto;
import br.com.fiaplanchespayment.application.ports.in.PaymentOrderPortIn;
import br.com.fiaplanchespayment.application.ports.out.NotifyPaymentTopicPortOut;
import br.com.fiaplanchespayment.application.ports.out.PaymentRepositoryPortOut;
import br.com.fiaplanchespayment.domain.enums.OrderStatus;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;

import java.time.LocalDateTime;
import java.util.Random;

@Slf4j
public class PaymentOrderUseCase implements PaymentOrderPortIn {

    private static final String[] ERROR_MESSAGES = {
            "Insufficient funds",
            "Card expired",
            "Invalid card number",
            "Suspicious activity detected",
            "Technical error"
    };
    private final Random random = new Random();
    private final PaymentRepositoryPortOut paymentRepositoryPortOut;
    private final NotifyPaymentTopicPortOut notifyPaymentTopicPortOut;

    public PaymentOrderUseCase(PaymentRepositoryPortOut paymentRepositoryPortOut, NotifyPaymentTopicPortOut notifyPaymentTopicPortOut) {
        this.paymentRepositoryPortOut = paymentRepositoryPortOut;
        this.notifyPaymentTopicPortOut = notifyPaymentTopicPortOut;
    }

    @SneakyThrows
    @Override
    public void executePaymentOrder(PaymentOrderDto paymentOrderDto) {
        log.info("Executando o pagamento do pedido: {}", paymentOrderDto.orderId());
        if (!validatePaymentOrder(paymentOrderDto)) {
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
    @Retryable(value = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 1, maxDelay = 5))
    private boolean validatePaymentOrder(PaymentOrderDto paymentOrderDto) throws Exception {
        int chance = random.nextInt(100);
        if (chance <= 15) {
            int messageIndex = random.nextInt(ERROR_MESSAGES.length);
            log.error("Payment refused: {}", ERROR_MESSAGES[messageIndex]);
            throw new Exception(ERROR_MESSAGES[messageIndex]);
        }
        return true;
    }

    @Recover
    public void recover(Exception e, PaymentOrderDto paymentOrderDto) {
        notifyPaymentTopicPortOut.notifyPaymentOrder(new UpdatePaymentOrderDto(paymentOrderDto.orderId(), OrderStatus.PAGAMENTO_REJEITADO));
    }
}
