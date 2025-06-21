package com.example.shopberry.domain.paymenttypes;

import com.example.shopberry.common.constants.messages.PaymentTypeMessages;
import com.example.shopberry.domain.paymenttypes.dto.CreatePaymentTypeRequestDto;
import com.example.shopberry.domain.paymenttypes.dto.PaymentTypeDtoMapper;
import com.example.shopberry.domain.paymenttypes.dto.PaymentTypeResponseDto;
import com.example.shopberry.domain.paymenttypes.dto.UpdatePaymentTypeRequestDto;
import jakarta.persistence.EntityExistsException;
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
    public PaymentTypeResponseDto getPaymentTypeById(Long paymentTypeId) throws EntityNotFoundException {
        PaymentType paymentType = paymentTypeRepository.findById(paymentTypeId).orElse(null);

        if (paymentType == null) {
            throw new EntityNotFoundException(PaymentTypeMessages.PAYMENT_TYPE_NOT_FOUND);
        }

        return paymentTypeDtoMapper.toDto(paymentType);
    }

    @Transactional
    public PaymentTypeResponseDto createPaymentType(CreatePaymentTypeRequestDto createPaymentTypeRequestDto) throws EntityExistsException {
        String paymentName = createPaymentTypeRequestDto.getPaymentName().trim();

        if (paymentTypeRepository.existsByPaymentName(paymentName)) {
            throw new EntityExistsException(PaymentTypeMessages.PAYMENT_TYPE_ALREADY_EXISTS);
        }

        PaymentType paymentType = new PaymentType();

        paymentType.setPaymentName(paymentName);

        return paymentTypeDtoMapper.toDto(paymentTypeRepository.save(paymentType));
    }

    @Transactional
    public PaymentTypeResponseDto updatePaymentTypeById(Long paymentTypeId, UpdatePaymentTypeRequestDto updatePaymentTypeRequestDto) throws EntityNotFoundException, EntityExistsException {
        PaymentType paymentType = paymentTypeRepository.findById(paymentTypeId).orElse(null);

        if (paymentType == null) {
            throw new EntityNotFoundException(PaymentTypeMessages.PAYMENT_TYPE_NOT_FOUND);
        }

        if (updatePaymentTypeRequestDto.getPaymentName() != null) {
            String paymentName = updatePaymentTypeRequestDto.getPaymentName().trim();

            PaymentType otherPaymentType = paymentTypeRepository.findByPaymentName(paymentName).orElse(null);

            if (otherPaymentType != null && !otherPaymentType.getPaymentTypeId().equals(paymentTypeId)) {
                throw new EntityExistsException(PaymentTypeMessages.PAYMENT_TYPE_ALREADY_EXISTS);
            }

            paymentType.setPaymentName(paymentName);
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
