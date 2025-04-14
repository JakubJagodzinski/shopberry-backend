package com.example.internet_shop.favourites;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class FavouriteId {

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "product_id")
    private Long productId;

}
