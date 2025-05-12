package com.example.internet_shop.reviews.dto;

import com.example.internet_shop.reviews.Review;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReviewDtoMapper {

    public ReviewResponseDto toDto(Review review) {
        ReviewResponseDto reviewResponseDto = new ReviewResponseDto();

        reviewResponseDto.setReviewId(review.getReviewId());
        reviewResponseDto.setProductId(review.getProduct().getProductId());
        reviewResponseDto.setCustomerId(review.getCustomer().getCustomerId());
        reviewResponseDto.setRatingValue(review.getRatingValue());
        reviewResponseDto.setReviewText(review.getReviewText());
        reviewResponseDto.setReviewedAt(review.getReviewedAt());
        reviewResponseDto.setIsApproved(review.getIsApproved());

        return reviewResponseDto;
    }

    public List<ReviewResponseDto> toDtoList(List<Review> reviews) {
        return reviews.stream()
                .map(this::toDto)
                .toList();
    }

}
