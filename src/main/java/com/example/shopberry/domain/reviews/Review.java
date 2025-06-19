package com.example.shopberry.domain.reviews;

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
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Customer customer;

    @Column(name = "rating_value", nullable = false)
    private Double ratingValue;

    @Column(name = "review_text", length = 1000)
    private String reviewText = "";

    @Column(name = "reviewed_at", nullable = false)
    private LocalDateTime reviewedAt = LocalDateTime.now();

    @Column(name = "is_approved", nullable = false)
    private Boolean isApproved = false;

}
