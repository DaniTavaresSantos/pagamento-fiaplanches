package br.com.fiaplanchespayment.infra.adapters.out;

import br.com.fiaplanchespayment.application.dtos.PaymentDto;
import br.com.fiaplanchespayment.application.ports.out.PaymentRepositoryPortOut;
import br.com.fiaplanchespayment.infra.mapper.PaymentMapper;
import br.com.fiaplanchespayment.infra.repository.PostGresPaymentRepository;
import br.com.fiaplanchespayment.infra.repository.entity.PaymentEntity;
import org.springframework.stereotype.Component;

@Component
public class PaymentRepositoryAdapterOut implements PaymentRepositoryPortOut {

    private final PaymentMapper paymentMapper;

    private final PostGresPaymentRepository postGresPaymentRepository;

    public PaymentRepositoryAdapterOut(PaymentMapper paymentMapper, PostGresPaymentRepository postGresPaymentRepository) {
        this.paymentMapper = paymentMapper;
        this.postGresPaymentRepository = postGresPaymentRepository;
    }

    @Override
    public PaymentDto save(PaymentDto paymentDto) {
        PaymentEntity paymentEntity = paymentMapper.paymentDtoToPaymentEntity(paymentDto);
        PaymentEntity paymentEntitySaved = postGresPaymentRepository.save(paymentEntity);
        return paymentMapper.paymentEntityToPaymentDto(paymentEntitySaved);
    }
}
