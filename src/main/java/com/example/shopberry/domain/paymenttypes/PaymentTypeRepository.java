package com.example.shopberry.domain.paymenttypes;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentTypeRepository extends JpaRepository<PaymentType, Long> {

    boolean existsByPaymentName(String paymentName);

    Optional<PaymentType> findByPaymentName(String paymentName);

}
