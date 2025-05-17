package com.example.shopberry.domain.promotions;

import com.example.shopberry.domain.promotions.dto.CreatePromotionRequestDto;
import com.example.shopberry.domain.promotions.dto.PromotionResponseDto;
import com.example.shopberry.domain.promotions.dto.UpdatePromotionRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/promotions")
public class PromotionController {

    private final PromotionService promotionService;

    public PromotionController(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    @GetMapping("/")
    public ResponseEntity<List<PromotionResponseDto>> getPromotions() {
        List<PromotionResponseDto> promotionResponseDtoList = promotionService.getPromotions();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(promotionResponseDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PromotionResponseDto> getPromotionById(@PathVariable Long id) {
        PromotionResponseDto promotionResponseDto = promotionService.getPromotionById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(promotionResponseDto);
    }

    @PostMapping("/")
    public ResponseEntity<PromotionResponseDto> createPromotion(@RequestBody CreatePromotionRequestDto createPromotionRequestDto) {
        PromotionResponseDto createdPromotionResponseDto = promotionService.createPromotion(createPromotionRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/promotions/" + createdPromotionResponseDto.getPromotionId()))
                .body(createdPromotionResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PromotionResponseDto> updatePromotionById(@PathVariable Long id, @RequestBody UpdatePromotionRequestDto updatePromotionRequestDto) {
        PromotionResponseDto updatedPromotionResponseDto = promotionService.updatePromotionById(id, updatePromotionRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedPromotionResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePromotionById(@PathVariable Long id) {
        promotionService.deletePromotionById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Promotion with id " + id + " deleted successfully");
    }

}
