package br.com.fiaplanchespayment.domain;

import java.time.LocalDateTime;
import java.util.List;

public class Order {

    private Long id;

    private String cpf;

    private List<Long> products;

    private LocalDateTime dateOrder;

    private String orderStatus;

    private Boolean isApproved = false;

    public Order() {
    }

    public Order(Long id, String cpf, List<Long> products, LocalDateTime dateOrder, String orderStatus, Boolean isApproved) {
        this.id = id;
        this.cpf = cpf;
        this.products = products;
        this.dateOrder = dateOrder;
        this.orderStatus = orderStatus;
        this.isApproved = isApproved;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public List<Long> getProducts() {
        return products;
    }

    public void setProducts(List<Long> products) {
        this.products = products;
    }

    public LocalDateTime getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(LocalDateTime dateOrder) {
        this.dateOrder = dateOrder;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Boolean getApproved() {
        return isApproved;
    }

    public void setApproved(Boolean approved) {
        isApproved = approved;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", cpf='" + cpf + '\'' +
                ", products=" + products +
                ", dateOrder=" + dateOrder +
                ", orderStatus='" + orderStatus + '\'' +
                ", isApproved=" + isApproved +
                '}';
    }
}