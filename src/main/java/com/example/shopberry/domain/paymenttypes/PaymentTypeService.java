package com.example.shopberry.domain.paymenttypes;

import com.example.shopberry.common.constants.messages.PaymentTypeMessages;
import com.example.shopberry.domain.paymenttypes.dto.CreatePaymentTypeRequestDto;
import com.example.shopberry.domain.paymenttypes.dto.PaymentTypeDtoMapper;
import com.example.shopberry.domain.paymenttypes.dto.PaymentTypeResponseDto;
import com.example.shopberry.domain.paymenttypes.dto.UpdatePaymentTypeRequestDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentTypeService {

    private final PaymentTypeRepository paymentTypeRepository;

    private final PaymentTypeDtoMapper paymentTypeDtoMapper;

    public List<PaymentTypeResponseDto> getAllPaymentTypes() {
        return paymentTypeDtoMapper.toDtoList(paymentTypeRepository.findAll());
    }

    @Transactional
    public PaymentTypeResponseDto getPaymentTypeById(Long paymentTypeId) throws IllegalArgumentException {
        PaymentType paymentType = paymentTypeRepository.findById(paymentTypeId).orElse(null);

        if (paymentType == null) {
            throw new IllegalArgumentException(PaymentTypeMessages.PAYMENT_TYPE_NOT_FOUND);
        }

        return paymentTypeDtoMapper.toDto(paymentType);
    }

    @Transactional
    public PaymentTypeResponseDto createPaymentType(CreatePaymentTypeRequestDto createPaymentTypeRequestDto) throws IllegalArgumentException {
        if (paymentTypeRepository.existsByPaymentName(createPaymentTypeRequestDto.getPaymentName())) {
            throw new IllegalArgumentException(PaymentTypeMessages.PAYMENT_TYPE_ALREADY_EXISTS);
        }

        if (createPaymentTypeRequestDto.getPaymentName() == null) {
            throw new IllegalArgumentException(PaymentTypeMessages.PAYMENT_TYPE_NAME_CANNOT_BE_NULL);
        }

        if (createPaymentTypeRequestDto.getPaymentName().isEmpty()) {
            throw new IllegalArgumentException(PaymentTypeMessages.PAYMENT_TYPE_NAME_CANNOT_BE_EMPTY);
        }

        PaymentType paymentType = new PaymentType();

        paymentType.setPaymentName(createPaymentTypeRequestDto.getPaymentName());

        return paymentTypeDtoMapper.toDto(paymentTypeRepository.save(paymentType));
    }

    @Transactional
    public PaymentTypeResponseDto updatePaymentTypeById(Long paymentTypeId, UpdatePaymentTypeRequestDto updatePaymentTypeRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        PaymentType paymentType = paymentTypeRepository.findById(paymentTypeId).orElse(null);

        if (paymentType == null) {
            throw new EntityNotFoundException(PaymentTypeMessages.PAYMENT_TYPE_NOT_FOUND);
        }

        if (updatePaymentTypeRequestDto.getPaymentName() != null) {
            PaymentType otherPaymentType = paymentTypeRepository.findByPaymentName(updatePaymentTypeRequestDto.getPaymentName()).orElse(null);

            if (otherPaymentType != null && !otherPaymentType.getPaymentTypeId().equals(paymentTypeId)) {
                throw new IllegalArgumentException(PaymentTypeMessages.PAYMENT_TYPE_ALREADY_EXISTS);
            }

            if (updatePaymentTypeRequestDto.getPaymentName().isEmpty()) {
                throw new IllegalArgumentException(PaymentTypeMessages.PAYMENT_TYPE_NAME_CANNOT_BE_EMPTY);
            }

            paymentType.setPaymentName(updatePaymentTypeRequestDto.getPaymentName());
        }

        return paymentTypeDtoMapper.toDto(paymentTypeRepository.save(paymentType));
    }

    @Transactional
    public void deletePaymentTypeById(Long paymentTypeId) throws EntityNotFoundException {
        if (!paymentTypeRepository.existsById(paymentTypeId)) {
            throw new EntityNotFoundException(PaymentTypeMessages.PAYMENT_TYPE_NOT_FOUND);
        }

        paymentTypeRepository.deleteById(paymentTypeId);
    }

}
