package br.com.fiaplanchespayment.domain;

import br.com.fiaplanchespayment.application.dtos.PaymentOrderDto;
import br.com.fiaplanchespayment.application.dtos.UpdatePaymentOrderDto;
import br.com.fiaplanchespayment.application.ports.out.NotifyPaymentTopicPortOut;
import br.com.fiaplanchespayment.domain.enums.OrderStatus;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.Random;
@Service
public class PaymentValidatorImpl implements PaymentValidatorPort {
    private static final String[] ERROR_MESSAGES = {
            "Insufficient funds",
            "Card expired",
            "Invalid card number",
            "Suspicious activity detected",
            "Technical error"
    };
    private final Random random = new Random();
    private final NotifyPaymentTopicPortOut notifyPaymentTopicPortOut;

    public PaymentValidatorImpl(NotifyPaymentTopicPortOut notifyPaymentTopicPortOut1) {
        this.notifyPaymentTopicPortOut = notifyPaymentTopicPortOut1;
    }

    @Retryable(value = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 1, maxDelay = 5))
    public boolean validatePaymentOrder(PaymentOrderDto paymentOrderDto) throws Exception {
        int chance = random.nextInt(100);
        if (chance <= 15) {
            int messageIndex = random.nextInt(ERROR_MESSAGES.length);
            throw new Exception(ERROR_MESSAGES[messageIndex]);
        }
        return true;
    }

    @Recover
    public void recover(Exception e, PaymentOrderDto paymentOrderDto) {
        notifyPaymentTopicPortOut.notifyPaymentOrder(new UpdatePaymentOrderDto(paymentOrderDto.orderId(), OrderStatus.PAGAMENTO_REJEITADO));
    }
}
