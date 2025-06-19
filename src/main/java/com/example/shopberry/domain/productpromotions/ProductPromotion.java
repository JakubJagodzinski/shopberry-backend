package com.example.shopberry.domain.productpromotions;

import com.example.shopberry.domain.products.Product;
import com.example.shopberry.domain.promotions.Promotion;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "product_promotions")
public class ProductPromotion {

    @EmbeddedId
    private ProductPromotionId id;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "fk_product_promotions_product"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    @ManyToOne
    @MapsId("promotionId")
    @JoinColumn(name = "promotion_id", foreignKey = @ForeignKey(name = "fk_product_promotions_promotion"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Promotion promotion;

}
