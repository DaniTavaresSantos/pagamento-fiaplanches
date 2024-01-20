package br.com.fiaplanchespayment.application.dtos;

import br.com.fiaplanchespayment.domain.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public record PaymentDto(
        Long orderId,
        OrderStatus status,
        BigDecimal value,
        String paymentMethod,
        LocalDateTime createDate
) {

    public PaymentDto(Long orderId, OrderStatus status, BigDecimal value, String paymentMethod, LocalDateTime createDate) {
        this.orderId = orderId;
        this.status = status;
        this.value = value;
        this.paymentMethod = paymentMethod;
        this.createDate = createDate;
    }
}
