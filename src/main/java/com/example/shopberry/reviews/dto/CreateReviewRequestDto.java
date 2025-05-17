package com.example.shopberry.reviews.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateReviewRequestDto {

    private Long productId;

    private Long customerId;

    private Double ratingValue;

    private String reviewText;

}
