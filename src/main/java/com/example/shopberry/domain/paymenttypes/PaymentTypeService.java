package com.example.shopberry.domain.paymenttypes;

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

    private static final String PAYMENT_TYPE_NOT_FOUND_MESSAGE = "Payment type not found";
    private static final String PAYMENT_TYPE_ALREADY_EXISTS_MESSAGE = "Payment type already exists";
    private static final String PAYMENT_TYPE_NAME_CANNOT_BE_NULL_MESSAGE = "Payment type name cannot be null";
    private static final String PAYMENT_TYPE_NAME_CANNOT_BE_EMPTY_MESSAGE = "Payment type name cannot be empty";

    public List<PaymentTypeResponseDto> getAllPaymentTypes() {
        return paymentTypeDtoMapper.toDtoList(paymentTypeRepository.findAll());
    }

    @Transactional
    public PaymentTypeResponseDto getPaymentTypeById(Long id) throws IllegalArgumentException {
        PaymentType paymentType = paymentTypeRepository.findById(id).orElse(null);

        if (paymentType == null) {
            throw new IllegalArgumentException(PAYMENT_TYPE_NOT_FOUND_MESSAGE);
        }

        return paymentTypeDtoMapper.toDto(paymentType);
    }

    @Transactional
    public PaymentTypeResponseDto createPaymentType(CreatePaymentTypeRequestDto createPaymentTypeRequestDto) throws IllegalArgumentException {
        if (paymentTypeRepository.existsByPaymentName(createPaymentTypeRequestDto.getPaymentName())) {
            throw new IllegalArgumentException(PAYMENT_TYPE_ALREADY_EXISTS_MESSAGE);
        }

        if (createPaymentTypeRequestDto.getPaymentName() == null) {
            throw new IllegalArgumentException(PAYMENT_TYPE_NAME_CANNOT_BE_NULL_MESSAGE);
        }

        if (createPaymentTypeRequestDto.getPaymentName().isEmpty()) {
            throw new IllegalArgumentException(PAYMENT_TYPE_NAME_CANNOT_BE_EMPTY_MESSAGE);
        }

        PaymentType paymentType = new PaymentType();

        paymentType.setPaymentName(createPaymentTypeRequestDto.getPaymentName());

        return paymentTypeDtoMapper.toDto(paymentTypeRepository.save(paymentType));
    }

    @Transactional
    public PaymentTypeResponseDto updatePaymentTypeById(Long id, UpdatePaymentTypeRequestDto updatePaymentTypeRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        PaymentType paymentType = paymentTypeRepository.findById(id).orElse(null);

        if (paymentType == null) {
            throw new EntityNotFoundException(PAYMENT_TYPE_NOT_FOUND_MESSAGE);
        }

        if (updatePaymentTypeRequestDto.getPaymentName() != null) {
            PaymentType otherPaymentType = paymentTypeRepository.findByPaymentName(updatePaymentTypeRequestDto.getPaymentName()).orElse(null);

            if (otherPaymentType != null && !otherPaymentType.getPaymentTypeId().equals(id)) {
                throw new IllegalArgumentException(PAYMENT_TYPE_ALREADY_EXISTS_MESSAGE);
            }

            if (updatePaymentTypeRequestDto.getPaymentName().isEmpty()) {
                throw new IllegalArgumentException(PAYMENT_TYPE_NAME_CANNOT_BE_EMPTY_MESSAGE);
            }

            paymentType.setPaymentName(updatePaymentTypeRequestDto.getPaymentName());
        }

        return paymentTypeDtoMapper.toDto(paymentTypeRepository.save(paymentType));
    }

    @Transactional
    public void deletePaymentTypeById(Long id) throws EntityNotFoundException {
        if (!paymentTypeRepository.existsById(id)) {
            throw new EntityNotFoundException(PAYMENT_TYPE_NOT_FOUND_MESSAGE);
        }

        paymentTypeRepository.deleteById(id);
    }

}
