package br.com.fiaplanchespayment.application.dtos;

import br.com.fiaplanchespayment.domain.PaymentOrder;

import java.math.BigDecimal;

public record PaymentOrderDto(
        Long orderId,
        String paymentMethod,
        BigDecimal paymentValue
) {
    public PaymentOrderDto(Long orderId, String paymentMethod, BigDecimal paymentValue) {
        this.orderId = orderId;
        this.paymentMethod = paymentMethod;
        this.paymentValue = paymentValue;
    }

    public static PaymentOrderDto toPaymentOrderDto(PaymentOrder paymentOrder) {
        return new PaymentOrderDto(
                paymentOrder.getOrderId(),
                paymentOrder.getPaymentMethod(),
                paymentOrder.getPaymentValue()
        );
    }

    public static PaymentOrder toPaymentOrder(PaymentOrderDto paymentOrderDto) {
        return new PaymentOrder(
                paymentOrderDto.orderId(),
                paymentOrderDto.paymentMethod(),
                paymentOrderDto.paymentValue()
        );
    }
}
