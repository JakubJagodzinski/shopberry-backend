package com.example.shopberry.domain.paymenttypes;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "payment_types")
public class PaymentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_type_id")
    private Long paymentTypeId;

    @Column(name = "payment_name", nullable = false, unique = true, length = 100)
    private String paymentName;

}
