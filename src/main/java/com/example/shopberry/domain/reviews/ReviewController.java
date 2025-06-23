package com.example.shopberry.domain.reviews;

import com.example.shopberry.auth.access.CheckPermission;
import com.example.shopberry.domain.reviews.dto.request.CreateReviewRequestDto;
import com.example.shopberry.domain.reviews.dto.request.UpdateReviewRequestDto;
import com.example.shopberry.domain.reviews.dto.response.ReviewResponseDto;
import com.example.shopberry.exception.ApiError;
import com.example.shopberry.user.Permission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get product all reviews")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of product reviews",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ReviewResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @GetMapping("/products/{productId}/reviews")
    public ResponseEntity<List<ReviewResponseDto>> getProductAllReviews(@PathVariable Long productId, @RequestParam(required = false) Boolean approvedOnly) {
        List<ReviewResponseDto> reviewResponseDtoList = reviewService.getProductAllReviews(productId, approvedOnly);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reviewResponseDtoList);
    }

    @Operation(summary = "Get customer all reviews")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of customer reviews",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ReviewResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Customer not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @GetMapping("/customers/{customerId}/reviews")
    public ResponseEntity<List<ReviewResponseDto>> getCustomerAllReviews(@PathVariable UUID customerId) {
        List<ReviewResponseDto> reviewResponseDtoList = reviewService.getCustomerAllReviews(customerId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reviewResponseDtoList);
    }

    @Operation(summary = "Get review by id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Review found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ReviewResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Review not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @GetMapping("/reviews/{reviewId}")
    public ResponseEntity<ReviewResponseDto> getReviewById(@PathVariable Long reviewId) {
        ReviewResponseDto reviewResponseDto = reviewService.getReviewById(reviewId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reviewResponseDto);
    }

    @Operation(summary = "Create new review for product")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "New review created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ReviewResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access Denied",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product / customer not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.REVIEW_CREATE)
    @PostMapping("/products/{productId}/reviews")
    public ResponseEntity<ReviewResponseDto> createReview(@PathVariable Long productId, @Valid @RequestBody CreateReviewRequestDto createReviewRequestDto) {
        ReviewResponseDto createdReviewResponseDto = reviewService.createReview(productId, createReviewRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/reviews/" + createdReviewResponseDto.getReviewId()))
                .body(createdReviewResponseDto);
    }

    @Operation(summary = "Update review by id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Review updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ReviewResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access Denied",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Review not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.REVIEW_UPDATE)
    @PatchMapping("/reviews/{reviewId}")
    public ResponseEntity<ReviewResponseDto> updateReviewById(@PathVariable Long reviewId, @Valid @RequestBody UpdateReviewRequestDto updateReviewRequestDto) {
        ReviewResponseDto updatedReviewResponseDto = reviewService.updateReviewById(reviewId, updateReviewRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedReviewResponseDto);
    }

    @Operation(summary = "Delete review by id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Review deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access Denied",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Review not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.REVIEW_DELETE)
    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<Void> deleteReviewById(@PathVariable Long reviewId) {
        reviewService.deleteReviewById(reviewId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @Operation(summary = "Delete customer all reviews")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "All customer reviews deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access Denied",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Customer not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.CUSTOMER_REVIEW_DELETE_ALL)
    @DeleteMapping("/customers/{customerId}/reviews")
    public ResponseEntity<Void> deleteCustomerAllReviews(@PathVariable UUID customerId) {
        reviewService.deleteCustomerAllReviews(customerId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @Operation(summary = "Delete product all reviews")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "All product reviews deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access Denied",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.PRODUCT_REVIEW_DELETE_ALL)
    @DeleteMapping("/products/{productId}/reviews")
    public ResponseEntity<Void> deleteProductAllReviews(@PathVariable Long productId) {
        reviewService.deleteProductAllReviews(productId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
