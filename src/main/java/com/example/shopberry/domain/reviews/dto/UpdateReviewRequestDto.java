package com.example.shopberry.domain.reviews.dto;

import com.example.shopberry.common.constants.messages.ReviewMessages;
import com.example.shopberry.common.validation.NotEmptyIfPresent;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateReviewRequestDto {

    @Schema(
            description = "Rating value from 1.0 to 5.0 given by the customer",
            example = "4.5",
            minimum = "1.0",
            maximum = "5.0",
            nullable = true
    )
    @Min(value = 1, message = ReviewMessages.RATING_VALUE_CANNOT_BE_LOWER_THAN_1)
    @Max(value = 5, message = ReviewMessages.RATING_VALUE_CANNOT_BE_HIGHER_THAN_5)
    @JsonProperty("rating_value")
    private Double ratingValue;

    @Schema(
            description = "Optional text of the review provided by the customer (max 1000 characters)",
            example = "Great product, fast delivery!",
            minLength = 1,
            maxLength = 1_000,
            nullable = true
    )
    @NotEmptyIfPresent
    @Size(max = 1_000, message = ReviewMessages.REVIEW_TEXT_CANNOT_BE_LONGER_THAN_1000_CHARACTERS)
    @JsonProperty("review_text")
    private String reviewText;

}
