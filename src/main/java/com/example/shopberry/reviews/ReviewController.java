package com.example.shopberry.reviews;

import com.example.shopberry.reviews.dto.CreateReviewRequestDto;
import com.example.shopberry.reviews.dto.ReviewResponseDto;
import com.example.shopberry.reviews.dto.UpdateReviewRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

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

    @PostMapping("/")
    public ResponseEntity<ReviewResponseDto> createReview(@RequestBody CreateReviewRequestDto createReviewRequestDto) {
        ReviewResponseDto createdReviewResponseDto = reviewService.createReview(createReviewRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/reviews/" + createdReviewResponseDto.getReviewId()))
                .body(createdReviewResponseDto);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewResponseDto> updateReviewById(@PathVariable Long reviewId, @RequestBody UpdateReviewRequestDto updateReviewRequestDto) {
        ReviewResponseDto updatedReviewResponseDto = reviewService.updateReviewById(reviewId, updateReviewRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedReviewResponseDto);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReviewById(reviewId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Review with id " + reviewId + " deleted successfully");
    }

    @DeleteMapping("/by-customer/{customerId}")
    public ResponseEntity<String> deleteReviewsByCustomerId(@PathVariable Long customerId) {
        reviewService.deleteReviewsByCustomerId(customerId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Reviews for customer with id " + customerId + " deleted successfully");
    }

    @DeleteMapping("/by-product/{productId}")
    public ResponseEntity<String> deleteReviewsByProductId(@PathVariable Long productId) {
        reviewService.deleteReviewsByProductId(productId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Reviews for product with id " + productId + " deleted successfully");
    }

}
