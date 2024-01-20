package br.com.fiaplanchespayment.application.ports.out;

import br.com.fiaplanchespayment.application.dtos.PaymentDto;

public interface NotifyPaymentTopicPortOut {

    void notifyPaymentOrder(PaymentDto paymentDto);
}
