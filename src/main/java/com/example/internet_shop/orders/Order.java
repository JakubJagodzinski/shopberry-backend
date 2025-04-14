package com.example.internet_shop.orders;

import com.example.internet_shop.customers.Customer;
import com.example.internet_shop.orderstatuses.OrderStatus;
import com.example.internet_shop.paymenttypes.PaymentType;
import com.example.internet_shop.shipmenttypes.ShipmentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    @ManyToOne
    @JoinColumn(name = "order_status_id", referencedColumnName = "order_status_id")
    private OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "shipment_type_id", referencedColumnName = "shipment_type_id")
    private ShipmentType shipmentType;

    @Column(name = "shipment_identifier", length = 100)
    private String shipmentIdentifier;

    @ManyToOne
    @JoinColumn(name = "payment_type_id", referencedColumnName = "payment_type_id")
    private PaymentType paymentType;

    @Column(name = "is_payment_recorded", nullable = false)
    private Boolean isPaymentRecorded;

    @Column(name = "is_invoice", nullable = false)
    private Boolean isInvoice;

    @PrePersist
    private void onCreate() {
        createdAt = LocalDateTime.now();
    }

}
