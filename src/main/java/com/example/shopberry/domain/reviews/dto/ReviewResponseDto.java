package com.example.shopberry.domain.reviews.dto;

import com.example.shopberry.domain.products.dto.ProductResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponseDto {

    @JsonProperty("review_id")
    private Long reviewId;

    private ProductResponseDto product;

    @JsonProperty("customer_id")
    private UUID customerId;

    @JsonProperty("rating_value")
    private Double ratingValue;

    @JsonProperty("review_text")
    private String reviewText;

    @JsonProperty("reviewed_at")
    private LocalDateTime reviewedAt;

    @JsonProperty("is_approved")
    private Boolean isApproved;

}
