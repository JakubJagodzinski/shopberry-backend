package com.example.shopberry.domain.favouriteproducts;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class FavouriteProductId {

    @Column(name = "customer_id")
    private UUID customerId;

    @Column(name = "product_id")
    private Long productId;

}
