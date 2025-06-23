package com.example.shopberry.domain.promotions;

import com.example.shopberry.auth.access.CheckPermission;
import com.example.shopberry.common.MessageResponseDto;
import com.example.shopberry.domain.promotions.dto.request.CreatePromotionRequestDto;
import com.example.shopberry.domain.promotions.dto.response.PromotionResponseDto;
import com.example.shopberry.domain.promotions.dto.request.UpdatePromotionRequestDto;
import com.example.shopberry.user.Permission;
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

    @GetMapping("/promotions")
    public ResponseEntity<List<PromotionResponseDto>> getAllPromotions() {
        List<PromotionResponseDto> promotionResponseDtoList = promotionService.getAllPromotions();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(promotionResponseDtoList);
    }

    @GetMapping("/promotions/{promotionId}")
    public ResponseEntity<PromotionResponseDto> getPromotionById(@PathVariable Long promotionId) {
        PromotionResponseDto promotionResponseDto = promotionService.getPromotionById(promotionId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(promotionResponseDto);
    }

    @CheckPermission(Permission.PROMOTION_CREATE)
    @PostMapping("/promotions")
    public ResponseEntity<PromotionResponseDto> createPromotion(@Valid @RequestBody CreatePromotionRequestDto createPromotionRequestDto) {
        PromotionResponseDto createdPromotionResponseDto = promotionService.createPromotion(createPromotionRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/promotions/" + createdPromotionResponseDto.getPromotionId()))
                .body(createdPromotionResponseDto);
    }

    @CheckPermission(Permission.PROMOTION_UPDATE)
    @PatchMapping("/promotions/{promotionId}")
    public ResponseEntity<PromotionResponseDto> updatePromotionById(@PathVariable Long promotionId, @Valid @RequestBody UpdatePromotionRequestDto updatePromotionRequestDto) {
        PromotionResponseDto updatedPromotionResponseDto = promotionService.updatePromotionById(promotionId, updatePromotionRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedPromotionResponseDto);
    }

    @CheckPermission(Permission.PROMOTION_DELETE)
    @DeleteMapping("/promotions/{promotionId}")
    public ResponseEntity<MessageResponseDto> deletePromotionById(@PathVariable Long promotionId) {
        promotionService.deletePromotionById(promotionId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Promotion with id " + promotionId + " deleted successfully"));
    }

}
