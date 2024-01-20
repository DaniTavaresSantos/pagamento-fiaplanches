package br.com.fiaplanchespayment.infra.mapper;

import br.com.fiaplanchespayment.application.dtos.PaymentDto;
import br.com.fiaplanchespayment.application.dtos.PaymentOrderDto;
import br.com.fiaplanchespayment.domain.PaymentOrder;
import br.com.fiaplanchespayment.infra.repository.entity.PaymentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(target = "id", ignore = true)
    PaymentEntity paymentDtoToPaymentEntity(PaymentDto paymentDto);

    PaymentDto paymentEntityToPaymentDto(PaymentEntity paymentEntity);

    PaymentOrderDto paymentOrderToPaymentDto(PaymentOrder paymentOrder);
}
