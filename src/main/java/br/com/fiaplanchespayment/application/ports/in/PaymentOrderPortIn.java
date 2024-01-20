package br.com.fiaplanchespayment.application.ports.in;

import br.com.fiaplanchespayment.application.dtos.PaymentOrderDto;

public interface PaymentOrderPortIn {

    void executePaymentOrder(PaymentOrderDto paymentOrderDto);
}
