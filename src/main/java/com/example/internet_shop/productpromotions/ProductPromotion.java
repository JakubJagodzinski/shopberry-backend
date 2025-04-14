package com.example.internet_shop.productpromotions;

import com.example.internet_shop.products.Product;
import com.example.internet_shop.promotions.Promotion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "product_promotions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class ProductPromotion {

    @EmbeddedId
    private ProductPromotionId id;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "fk_product"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    @ManyToOne
    @MapsId("promotionId")
    @JoinColumn(name = "promotion_id", foreignKey = @ForeignKey(name = "fk_promotion"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Promotion promotion;

}
