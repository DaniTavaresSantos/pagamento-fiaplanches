package br.com.fiaplanchespayment.infra.repository.entity;

import br.com.fiaplanchespayment.domain.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderId;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private BigDecimal value;
    private String paymentMethod;
    private LocalDateTime createDate;

}
