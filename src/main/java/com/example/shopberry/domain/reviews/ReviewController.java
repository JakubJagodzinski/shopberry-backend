package com.example.shopberry.domain.reviews;

import com.example.shopberry.auth.access.CheckPermission;
import com.example.shopberry.common.MessageResponseDto;
import com.example.shopberry.domain.reviews.dto.request.CreateReviewRequestDto;
import com.example.shopberry.domain.reviews.dto.response.ReviewResponseDto;
import com.example.shopberry.domain.reviews.dto.request.UpdateReviewRequestDto;
import com.example.shopberry.user.Permission;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/products/{productId}/reviews")
    public ResponseEntity<List<ReviewResponseDto>> getProductAllReviews(@PathVariable Long productId, @RequestParam(required = false) Boolean approvedOnly) {
        List<ReviewResponseDto> reviewResponseDtoList = reviewService.getProductAllReviews(productId, approvedOnly);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reviewResponseDtoList);
    }

    @GetMapping("/customers/{customerId}/reviews")
    public ResponseEntity<List<ReviewResponseDto>> getCustomerAllReviews(@PathVariable UUID customerId) {
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

    @CheckPermission(Permission.REVIEW_CREATE)
    @PostMapping("/products/{productId}/reviews")
    public ResponseEntity<ReviewResponseDto> createReview(@PathVariable Long productId, @Valid @RequestBody CreateReviewRequestDto createReviewRequestDto) {
        ReviewResponseDto createdReviewResponseDto = reviewService.createReview(productId, createReviewRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/reviews/" + createdReviewResponseDto.getReviewId()))
                .body(createdReviewResponseDto);
    }

    @CheckPermission(Permission.REVIEW_UPDATE)
    @PatchMapping("/reviews/{reviewId}")
    public ResponseEntity<ReviewResponseDto> updateReviewById(@PathVariable Long reviewId, @Valid @RequestBody UpdateReviewRequestDto updateReviewRequestDto) {
        ReviewResponseDto updatedReviewResponseDto = reviewService.updateReviewById(reviewId, updateReviewRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedReviewResponseDto);
    }

    @CheckPermission(Permission.REVIEW_DELETE)
    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<MessageResponseDto> deleteReviewById(@PathVariable Long reviewId) {
        reviewService.deleteReviewById(reviewId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Review with id " + reviewId + " deleted successfully"));
    }

    @CheckPermission(Permission.CUSTOMER_REVIEW_DELETE_ALL)
    @DeleteMapping("/customers/{customerId}/reviews")
    public ResponseEntity<MessageResponseDto> deleteCustomerAllReviews(@PathVariable UUID customerId) {
        reviewService.deleteCustomerAllReviews(customerId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("All reviews from customer with id " + customerId + " deleted successfully"));
    }

    @CheckPermission(Permission.PRODUCT_REVIEW_DELETE_ALL)
    @DeleteMapping("/products/{productId}/reviews")
    public ResponseEntity<MessageResponseDto> deleteProductAllReviews(@PathVariable Long productId) {
        reviewService.deleteProductAllReviews(productId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("All reviews for product with id " + productId + " deleted successfully"));
    }

}
