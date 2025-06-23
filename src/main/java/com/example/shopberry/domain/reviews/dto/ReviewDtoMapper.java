package com.example.shopberry.domain.reviews.dto;

import com.example.shopberry.domain.reviews.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReviewDtoMapper {

    public ReviewResponseDto toDto(Review review) {
        if (review == null) {
            return null;
        }

        ReviewResponseDto reviewResponseDto = new ReviewResponseDto();

        reviewResponseDto.setReviewId(review.getReviewId());
        if (review.getProduct() != null) {
            reviewResponseDto.setProductId(review.getProduct().getProductId());
        } else {
            reviewResponseDto.setProductId(null);
        }
        if (review.getCustomer() != null) {
            reviewResponseDto.setCustomerId(review.getCustomer().getUserId());
        } else {
            reviewResponseDto.setCustomerId(null);
        }
        reviewResponseDto.setRatingValue(review.getRatingValue());
        reviewResponseDto.setReviewText(review.getReviewText());
        reviewResponseDto.setReviewedAt(review.getReviewedAt());
        reviewResponseDto.setIsApproved(review.getIsApproved());

        return reviewResponseDto;
    }

    public List<ReviewResponseDto> toDtoList(List<Review> reviewList) {
        return reviewList.stream()
                .map(this::toDto)
                .toList();
    }

}
