package com.example.shopberry.domain.reviews.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
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
@JsonPropertyOrder({"review_id", "product_id", "customer_id", "rating_value", "review_text", "reviewed_at", "is_approved"})
public class ReviewResponseDto {

    @JsonProperty("review_id")
    private Long reviewId;

    @JsonProperty("product_id")
    private Long productId;

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
