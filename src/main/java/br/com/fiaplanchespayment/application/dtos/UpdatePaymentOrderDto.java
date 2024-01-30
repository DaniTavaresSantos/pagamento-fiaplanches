package br.com.fiaplanchespayment.application.dtos;

import br.com.fiaplanchespayment.domain.enums.OrderStatus;

public record UpdatePaymentOrderDto(
        Long orderId,
        OrderStatus status
) {
}
