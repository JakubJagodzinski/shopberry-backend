package com.example.internet_shop.paymenttypes;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentTypeService {

    private final PaymentTypeRepository paymentTypeRepository;

    private final PaymentTypeDtoMapper paymentTypeDtoMapper;

    private final String PAYMENT_TYPE_NOT_FOUND_MESSAGE = "Payment type not found";
    private final String PAYMENT_TYPE_ALREADY_EXISTS_MESSAGE = "Payment type already exists";
    private final String PAYMENT_TYPE_NAME_CANNOT_BE_NULL_MESSAGE = "Payment type name cannot be null";
    private final String PAYMENT_TYPE_NAME_CANNOT_BE_EMPTY_MESSAGE = "Payment type name cannot be empty";

    public PaymentTypeService(PaymentTypeRepository paymentTypeRepository, PaymentTypeDtoMapper paymentTypeDtoMapper) {
        this.paymentTypeRepository = paymentTypeRepository;
        this.paymentTypeDtoMapper = paymentTypeDtoMapper;
    }

    public List<PaymentTypeDto> getPaymentTypes() {
        return paymentTypeDtoMapper.toDtoList(paymentTypeRepository.findAll());
    }

    @Transactional
    public PaymentTypeDto getPaymentTypeById(Long id) {
        if (!paymentTypeRepository.existsById(id)) {
            throw new IllegalArgumentException(PAYMENT_TYPE_NOT_FOUND_MESSAGE);
        }

        return paymentTypeDtoMapper.toDto(paymentTypeRepository.getReferenceById(id));
    }

    @Transactional
    public PaymentTypeDto createPaymentType(CreatePaymentTypeDto createPaymentTypeDto) throws IllegalArgumentException {
        if (paymentTypeRepository.existsByPaymentName(createPaymentTypeDto.getPaymentName())) {
            throw new IllegalArgumentException(PAYMENT_TYPE_ALREADY_EXISTS_MESSAGE);
        }

        if (createPaymentTypeDto.getPaymentName() == null) {
            throw new IllegalArgumentException(PAYMENT_TYPE_NAME_CANNOT_BE_NULL_MESSAGE);
        }

        if (createPaymentTypeDto.getPaymentName().isEmpty()) {
            throw new IllegalArgumentException(PAYMENT_TYPE_NAME_CANNOT_BE_EMPTY_MESSAGE);
        }

        PaymentType paymentType = new PaymentType();

        paymentType.setPaymentName(createPaymentTypeDto.getPaymentName());

        return paymentTypeDtoMapper.toDto(paymentTypeRepository.save(paymentType));
    }

    @Transactional
    public PaymentTypeDto updatePaymentTypeById(Long id, UpdatePaymentTypeDto updatePaymentTypeDto) throws EntityNotFoundException, IllegalArgumentException {
        if (!paymentTypeRepository.existsById(id)) {
            throw new EntityNotFoundException(PAYMENT_TYPE_NOT_FOUND_MESSAGE);
        }

        PaymentType paymentType = paymentTypeRepository.getReferenceById(id);

        if (updatePaymentTypeDto.getPaymentName() != null) {
            PaymentType otherPaymentType = paymentTypeRepository.findByPaymentName(updatePaymentTypeDto.getPaymentName());

            if (otherPaymentType != null && !otherPaymentType.getPaymentTypeId().equals(id)) {
                throw new IllegalArgumentException(PAYMENT_TYPE_ALREADY_EXISTS_MESSAGE);
            }

            if (updatePaymentTypeDto.getPaymentName().isEmpty()) {
                throw new IllegalArgumentException(PAYMENT_TYPE_NAME_CANNOT_BE_EMPTY_MESSAGE);
            }

            paymentType.setPaymentName(updatePaymentTypeDto.getPaymentName());
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
