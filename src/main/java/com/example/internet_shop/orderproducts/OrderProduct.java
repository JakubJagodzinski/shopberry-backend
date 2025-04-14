package com.example.internet_shop.orderproducts;

import com.example.internet_shop.orderproductstatuses.OrderProductStatus;
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
@Table(name = "order_products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class OrderProduct {

    @EmbeddedId
    private OrderProductId orderProductId;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id", foreignKey = @ForeignKey(name = "fk_order"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Order order;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "fk_product"))
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
