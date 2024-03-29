package br.com.fiaplanchespayment.infra.config.beans;

import br.com.fiaplanchespayment.application.ports.out.NotifyPaymentTopicPortOut;
import br.com.fiaplanchespayment.application.ports.out.PaymentRepositoryPortOut;
import br.com.fiaplanchespayment.application.usecases.PaymentOrderUseCase;
import br.com.fiaplanchespayment.domain.PaymentValidatorPort;
import lombok.Generated;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Generated
@Configuration
public class PaymentOrderConfig {

    @Bean
    public PaymentOrderUseCase paymentOrderUseCase(PaymentRepositoryPortOut paymentRepositoryPortOut,
                                                   NotifyPaymentTopicPortOut notifyPaymentTopicPortOut,
                                                   PaymentValidatorPort paymentValidatorPort) {
        return new PaymentOrderUseCase(paymentRepositoryPortOut, notifyPaymentTopicPortOut, paymentValidatorPort);
    }
}
