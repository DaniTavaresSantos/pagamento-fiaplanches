package br.com.fiaplanchespayment.infra.config.beans;

import br.com.fiaplanchespayment.application.ports.out.NotifyPaymentTopicPortOut;
import br.com.fiaplanchespayment.application.ports.out.PaymentRepositoryPortOut;
import br.com.fiaplanchespayment.application.usecases.PaymentOrderUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentOrderConfig {

    @Bean
    public PaymentOrderUseCase paymentOrderUseCase(PaymentRepositoryPortOut paymentRepositoryPortOut,
                                                   NotifyPaymentTopicPortOut notifyPaymentTopicPortOut) {
        return new PaymentOrderUseCase(paymentRepositoryPortOut, notifyPaymentTopicPortOut);
    }
}
