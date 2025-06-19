package com.example.shopberry.domain.reviews.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateReviewRequestDto {

    @JsonProperty("customer_id")
    private UUID customerId;

    @JsonProperty("rating_value")
    private Double ratingValue;

    @JsonProperty("review_text")
    private String reviewText;

}
