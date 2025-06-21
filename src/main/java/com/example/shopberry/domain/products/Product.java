package com.example.shopberry.domain.products;

import com.example.shopberry.domain.categories.Category;
import com.example.shopberry.domain.producers.Producer;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name", nullable = false, unique = true, length = 100)
    private String productName;

    @Column(name = "product_price", nullable = false)
    private Double productPrice;

    @Column(name = "discount_percent_value")
    private Double discountPercentValue = 0.0;

    @Column(name = "rating_value")
    private Double ratingValue = 0.0;

    @Column(name = "ratings_count")
    private Long ratingsCount = 0L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producer_id", foreignKey = @ForeignKey(name = "fk_products_producer"))
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Producer producer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "fk_products_category"))
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Category category;

    @Column(name = "is_in_stock", nullable = false)
    private Boolean isInStock = true;

    @Lob
    private byte[] image = null;

}
