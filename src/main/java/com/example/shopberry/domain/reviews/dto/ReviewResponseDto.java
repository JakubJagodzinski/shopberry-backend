package com.example.shopberry.domain.reviews.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponseDto {

    private Long reviewId;

    private Long productId;

    private Long customerId;

    private Double ratingValue;

    private String reviewText;

    private LocalDateTime reviewedAt;

    private Boolean isApproved;

}
