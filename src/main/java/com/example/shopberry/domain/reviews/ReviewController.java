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
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/by-product/{productId}")
    public ResponseEntity<List<ReviewResponseDto>> getReviewsByProductId(@PathVariable Long productId) {
        List<ReviewResponseDto> reviewResponseDtoList = reviewService.getReviewsByProductId(productId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reviewResponseDtoList);
    }

    @GetMapping("/by-customer/{customerId}")
    public ResponseEntity<List<ReviewResponseDto>> getReviewsByCustomerId(@PathVariable Long customerId) {
        List<ReviewResponseDto> reviewResponseDtoList = reviewService.getReviewsByCustomerId(customerId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reviewResponseDtoList);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewResponseDto> getReviewById(@PathVariable Long reviewId) {
        ReviewResponseDto reviewResponseDto = reviewService.getReviewById(reviewId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reviewResponseDto);
    }

    @PostMapping
    public ResponseEntity<ReviewResponseDto> createReview(@RequestBody CreateReviewRequestDto createReviewRequestDto) {
        ReviewResponseDto createdReviewResponseDto = reviewService.createReview(createReviewRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/reviews/" + createdReviewResponseDto.getReviewId()))
                .body(createdReviewResponseDto);
    }

    @PatchMapping("/{reviewId}")
    public ResponseEntity<ReviewResponseDto> updateReviewById(@PathVariable Long reviewId, @RequestBody UpdateReviewRequestDto updateReviewRequestDto) {
        ReviewResponseDto updatedReviewResponseDto = reviewService.updateReviewById(reviewId, updateReviewRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedReviewResponseDto);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<MessageResponseDto> deleteReviewById(@PathVariable Long reviewId) {
        reviewService.deleteReviewById(reviewId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Review with id " + reviewId + " deleted successfully"));
    }

    @DeleteMapping("/by-customer/{customerId}")
    public ResponseEntity<MessageResponseDto> deleteReviewsByCustomerId(@PathVariable Long customerId) {
        reviewService.deleteReviewsByCustomerId(customerId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Reviews for customer with id " + customerId + " deleted successfully"));
    }

    @DeleteMapping("/by-product/{productId}")
    public ResponseEntity<MessageResponseDto> deleteReviewsByProductId(@PathVariable Long productId) {
        reviewService.deleteReviewsByProductId(productId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Reviews for product with id " + productId + " deleted successfully"));
    }

}
