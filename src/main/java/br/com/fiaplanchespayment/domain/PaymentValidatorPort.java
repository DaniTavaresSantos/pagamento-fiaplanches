package br.com.fiaplanchespayment.domain;

import br.com.fiaplanchespayment.application.dtos.PaymentOrderDto;

public interface PaymentValidatorPort {
    boolean validatePaymentOrder(PaymentOrderDto paymentOrderDto) throws Exception;
}
