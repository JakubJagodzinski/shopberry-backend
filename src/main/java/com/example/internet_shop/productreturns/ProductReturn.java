package com.example.internet_shop.productreturns;

import com.example.internet_shop.causesofreturns.CauseOfReturn;
import com.example.internet_shop.orders.Order;
import com.example.internet_shop.products.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "order_returns")
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class ProductReturn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_return_id")
    private Long productReturnId;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id", foreignKey = @ForeignKey(name = "fk_order"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Order order;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "cause_of_return_id", referencedColumnName = "cause_of_return_id", nullable = false)
    private CauseOfReturn causeOfReturn;

}
