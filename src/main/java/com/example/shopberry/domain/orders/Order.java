package com.example.shopberry.domain.orders;

import com.example.shopberry.domain.customers.Customer;
import com.example.shopberry.domain.orderstatuses.OrderStatus;
import com.example.shopberry.domain.paymenttypes.PaymentType;
import com.example.shopberry.domain.shipmenttypes.ShipmentType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "sent_at")
    private LocalDateTime sentAt = null;

    @ManyToOne
    @JoinColumn(name = "order_status_id", foreignKey = @ForeignKey(name = "fk_orders_order_status"))
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "fk_orders_customer"))
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "shipment_type_id", foreignKey = @ForeignKey(name = "fk_orders_shipment_type"))
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private ShipmentType shipmentType;

    @Column(name = "shipment_identifier", length = 100)
    private String shipmentIdentifier = null;

    @ManyToOne
    @JoinColumn(name = "payment_type_id", foreignKey = @ForeignKey(name = "fk_orders_payment_type"))
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private PaymentType paymentType;

    @Column(name = "is_payment_recorded", nullable = false)
    private Boolean isPaymentRecorded = false;

    @Column(name = "is_invoice", nullable = false)
    private Boolean isInvoice = false;

}
