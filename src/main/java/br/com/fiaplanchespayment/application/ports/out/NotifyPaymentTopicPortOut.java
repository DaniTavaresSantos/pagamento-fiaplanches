package br.com.fiaplanchespayment.application.ports.out;

import br.com.fiaplanchespayment.application.dtos.UpdatePaymentOrderDto;

public interface NotifyPaymentTopicPortOut {

    void notifyPaymentOrder(UpdatePaymentOrderDto updatePaymentOrderDto);
}
