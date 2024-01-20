package br.com.fiaplanchespayment.infra.repository;

import br.com.fiaplanchespayment.infra.repository.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostGresPaymentRepository extends JpaRepository<PaymentEntity, Long> {
}
