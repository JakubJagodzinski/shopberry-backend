package com.example.shopberry.domain.favouriteproducts;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class FavouriteProductId {

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "product_id")
    private Long productId;

}
