package com.example.shopberry.domain.reviews.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateReviewRequestDto {

    @JsonProperty("customer_id")
    private Long customerId;

    @JsonProperty("rating_value")
    private Double ratingValue;

    @JsonProperty("review_text")
    private String reviewText;

}
