package com.example.shopberry.domain.paymenttypes.dto;

import com.example.shopberry.domain.paymenttypes.PaymentType;
import com.example.shopberry.domain.paymenttypes.dto.response.PaymentTypeResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaymentTypeDtoMapper {

    public PaymentTypeResponseDto toDto(PaymentType paymentType) {
        if (paymentType == null) {
            return null;
        }

        PaymentTypeResponseDto dto = new PaymentTypeResponseDto();

        dto.setPaymentTypeId(paymentType.getPaymentTypeId());
        dto.setPaymentName(paymentType.getPaymentName());

        return dto;
    }

    public List<PaymentTypeResponseDto> toDtoList(List<PaymentType> paymentTypeList) {
        return paymentTypeList.stream()
                .map(this::toDto)
                .toList();
    }

}
