package com.example.shopberry.cartentries;

import com.example.shopberry.customers.Customer;
import com.example.shopberry.products.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Table(name = "cart_entries")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartEntry {

    @EmbeddedId
    private CartEntryId id;

    @ManyToOne
    @MapsId("customerId")
    @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "fk_cart_entry_customer"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Customer customer;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "fk_cart_entry_product"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    @Column(nullable = false)
    private Long quantity;

    @Column(nullable = false)
    private LocalDateTime addedAt = LocalDateTime.now();

}
