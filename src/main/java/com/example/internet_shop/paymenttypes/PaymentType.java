package com.example.internet_shop.paymenttypes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "payment_types")
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class PaymentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_type_id")
    private Long paymentTypeId;

    @Column(name = "payment_name")
    private String paymentName;

    @Column(name = "payment_cost")
    private Float paymentCost;

}
