package com.example.shopberry.domain.promotions;

import com.example.shopberry.auth.access.CheckPermission;
import com.example.shopberry.domain.promotions.dto.request.CreatePromotionRequestDto;
import com.example.shopberry.domain.promotions.dto.request.UpdatePromotionRequestDto;
import com.example.shopberry.domain.promotions.dto.response.PromotionResponseDto;
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

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
public class PromotionController {

    private final PromotionService promotionService;

    @Operation(summary = "Get all promotions")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of promotions",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PromotionResponseDto.class)
                    )
            )
    })
    @GetMapping("/promotions")
    public ResponseEntity<List<PromotionResponseDto>> getAllPromotions() {
        List<PromotionResponseDto> promotionResponseDtoList = promotionService.getAllPromotions();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(promotionResponseDtoList);
    }

    @Operation(summary = "Get promotion by id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Promotion found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PromotionResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Promotion not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
    })
    @GetMapping("/promotions/{promotionId}")
    public ResponseEntity<PromotionResponseDto> getPromotionById(@PathVariable Long promotionId) {
        PromotionResponseDto promotionResponseDto = promotionService.getPromotionById(promotionId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(promotionResponseDto);
    }

    @Operation(summary = "Create new promotions")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "New promotion created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PromotionResponseDto.class)
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
                    responseCode = "400",
                    description = "Promotion with that name already exists",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
    })
    @CheckPermission(Permission.PROMOTION_CREATE)
    @PostMapping("/promotions")
    public ResponseEntity<PromotionResponseDto> createPromotion(@Valid @RequestBody CreatePromotionRequestDto createPromotionRequestDto) {
        PromotionResponseDto createdPromotionResponseDto = promotionService.createPromotion(createPromotionRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/promotions/" + createdPromotionResponseDto.getPromotionId()))
                .body(createdPromotionResponseDto);
    }

    @Operation(summary = "Update promotion by id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Promotion updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PromotionResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Promotion with that name already exists",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
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
                    description = "Promotion not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
    })
    @CheckPermission(Permission.PROMOTION_UPDATE)
    @PatchMapping("/promotions/{promotionId}")
    public ResponseEntity<PromotionResponseDto> updatePromotionById(@PathVariable Long promotionId, @Valid @RequestBody UpdatePromotionRequestDto updatePromotionRequestDto) {
        PromotionResponseDto updatedPromotionResponseDto = promotionService.updatePromotionById(promotionId, updatePromotionRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedPromotionResponseDto);
    }

    @Operation(summary = "Delete promotion by id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Promotion deleted successfully"
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
                    description = "Promotion not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
    })
    @CheckPermission(Permission.PROMOTION_DELETE)
    @DeleteMapping("/promotions/{promotionId}")
    public ResponseEntity<Void> deletePromotionById(@PathVariable Long promotionId) {
        promotionService.deletePromotionById(promotionId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
