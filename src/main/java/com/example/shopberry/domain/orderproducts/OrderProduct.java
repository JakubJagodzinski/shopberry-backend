package com.example.shopberry.domain.orderproducts;

import com.example.shopberry.domain.orderproductstatuses.OrderProductStatus;
import com.example.shopberry.domain.orders.Order;
import com.example.shopberry.domain.products.Product;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "order_products")
public class OrderProduct {

    @EmbeddedId
    private OrderProductId id;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id", foreignKey = @ForeignKey(name = "fk_order_products_order"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Order order;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "fk_order_products_product"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    @Column(name = "product_quantity", nullable = false)
    private Long productQuantity;

    @Column(name = "product_price", nullable = false)
    private Double productPrice;

    @ManyToOne
    @JoinColumn(name = "order_product_status_id", foreignKey = @ForeignKey(name = "fk_order_products_order_product_status"))
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private OrderProductStatus orderProductStatus;

}
