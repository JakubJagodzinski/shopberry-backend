package com.example.internet_shop.paymenttypes;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaymentTypeDtoMapper {

    public PaymentTypeDto toDto(PaymentType paymentType) {
        PaymentTypeDto dto = new PaymentTypeDto();

        dto.setPaymentTypeId(paymentType.getPaymentTypeId());
        dto.setPaymentName(paymentType.getPaymentName());

        return dto;
    }

    public List<PaymentTypeDto> toDtoList(List<PaymentType> paymentTypes) {
        return paymentTypes.stream()
                .map(this::toDto)
                .toList();
    }

}
