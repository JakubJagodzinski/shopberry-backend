package com.example.internet_shop.products;

import com.example.internet_shop.producers.Producer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private Double ratingValue;

    @Column(name = "ratings_count")
    private Long ratingsCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producer_id", referencedColumnName = "producer_id")
    private Producer producer;

    @Column(name = "is_in_stock", nullable = false)
    private Boolean isInStock;

    @Lob
    private byte[] image;

}
