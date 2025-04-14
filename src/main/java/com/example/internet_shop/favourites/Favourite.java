package com.example.internet_shop.favourites;

import com.example.internet_shop.customers.Customer;
import com.example.internet_shop.products.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "favourites")
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Favourite {

    @EmbeddedId
    private FavouriteId favouriteId;

    @ManyToOne
    @MapsId("customerId")
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id", insertable = false, updatable = false)
    private Customer customer;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", insertable = false, updatable = false)
    private Product product;

    @Column(name = "added_at")
    private LocalDateTime addedAt;

    @PrePersist
    private void prePersist() {
        this.addedAt = LocalDateTime.now();
    }

}
