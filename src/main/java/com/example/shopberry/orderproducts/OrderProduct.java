package com.example.shopberry.orderproducts;

import com.example.shopberry.orderproductstatuses.OrderProductStatus;
import com.example.shopberry.orders.Order;
import com.example.shopberry.products.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "order_products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderProduct {

    @EmbeddedId
    private OrderProductId id;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id", foreignKey = @ForeignKey(name = "fk_order_product_order"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Order order;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "fk_order_product_product"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    @Column(name = "product_quantity", nullable = false)
    private Long productQuantity;

    @Column(name = "product_price", nullable = false)
    private Double productPrice;

    @ManyToOne
    @JoinColumn(name = "order_product_status_id", referencedColumnName = "order_product_status_id", nullable = false)
    private OrderProductStatus orderProductStatus;

}
