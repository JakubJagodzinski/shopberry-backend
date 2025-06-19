package com.example.shopberry.domain.reviews;

import com.example.shopberry.common.MessageResponseDto;
import com.example.shopberry.domain.reviews.dto.CreateReviewRequestDto;
import com.example.shopberry.domain.reviews.dto.ReviewResponseDto;
import com.example.shopberry.domain.reviews.dto.UpdateReviewRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/products/{productId}/reviews")
    public ResponseEntity<List<ReviewResponseDto>> getProductAllReviews(@PathVariable Long productId) {
        List<ReviewResponseDto> reviewResponseDtoList = reviewService.getProductAllReviews(productId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reviewResponseDtoList);
    }

    @GetMapping("/customers/{customerId}/reviews")
    public ResponseEntity<List<ReviewResponseDto>> getCustomerAllReviews(@PathVariable Long customerId) {
        List<ReviewResponseDto> reviewResponseDtoList = reviewService.getCustomerAllReviews(customerId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reviewResponseDtoList);
    }

    @GetMapping("/reviews/{reviewId}")
    public ResponseEntity<ReviewResponseDto> getReviewById(@PathVariable Long reviewId) {
        ReviewResponseDto reviewResponseDto = reviewService.getReviewById(reviewId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reviewResponseDto);
    }

    @PostMapping("/products/{productId}/reviews")
    public ResponseEntity<ReviewResponseDto> createReview(@PathVariable Long productId, @RequestBody CreateReviewRequestDto createReviewRequestDto) {
        ReviewResponseDto createdReviewResponseDto = reviewService.createReview(productId, createReviewRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/reviews/" + createdReviewResponseDto.getReviewId()))
                .body(createdReviewResponseDto);
    }

    @PatchMapping("/reviews/{reviewId}")
    public ResponseEntity<ReviewResponseDto> updateReviewById(@PathVariable Long reviewId, @RequestBody UpdateReviewRequestDto updateReviewRequestDto) {
        ReviewResponseDto updatedReviewResponseDto = reviewService.updateReviewById(reviewId, updateReviewRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedReviewResponseDto);
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<MessageResponseDto> deleteReviewById(@PathVariable Long reviewId) {
        reviewService.deleteReviewById(reviewId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Review with id " + reviewId + " deleted successfully"));
    }

    @DeleteMapping("/customers/{customerId}/reviews")
    public ResponseEntity<MessageResponseDto> deleteCustomerAllReviews(@PathVariable Long customerId) {
        reviewService.deleteCustomerAllReviews(customerId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("All reviews from customer with id " + customerId + " deleted successfully"));
    }

    @DeleteMapping("/products/{productId}/reviews")
    public ResponseEntity<MessageResponseDto> deleteProductAllReviews(@PathVariable Long productId) {
        reviewService.deleteProductAllReviews(productId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("All reviews for product with id " + productId + " deleted successfully"));
    }

}
