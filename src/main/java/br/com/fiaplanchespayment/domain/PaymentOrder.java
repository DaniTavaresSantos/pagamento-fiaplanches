package br.com.fiaplanchespayment.domain;

import java.math.BigDecimal;

public class PaymentOrder {

    private Long orderId;
    private String paymentMethod;
    private BigDecimal paymentValue;

    public PaymentOrder() {
    }

    public PaymentOrder(Long orderId, String paymentMethod, BigDecimal paymentValue) {
        this.orderId = orderId;
        this.paymentMethod = paymentMethod;
        this.paymentValue = paymentValue;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public BigDecimal getPaymentValue() {
        return paymentValue;
    }

    public void setPaymentValue(BigDecimal paymentValue) {
        this.paymentValue = paymentValue;
    }

    @Override
    public String toString() {
        return "PaymentOrder{" +
                "orderId=" + orderId +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", paymentValue=" + paymentValue +
                '}';
    }
}
