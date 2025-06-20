package com.example.shopberry.domain.cartproducts;

import com.example.shopberry.domain.customers.Customer;
import com.example.shopberry.domain.products.Product;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "cart_products")
public class CartProduct {

    @EmbeddedId
    private CartProductId id;

    @ManyToOne
    @MapsId("customerId")
    @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "fk_cart_products_customer"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Customer customer;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "fk_cart_products_product"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    @Column(name = "product_quantity", nullable = false)
    private Long productQuantity;

    @Column(nullable = false)
    private LocalDateTime addedAt = LocalDateTime.now();

}
