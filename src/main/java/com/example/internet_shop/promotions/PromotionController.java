package com.example.internet_shop.promotions;

import com.example.internet_shop.promotions.dto.CreatePromotionRequestDto;
import com.example.internet_shop.promotions.dto.PromotionResponseDto;
import com.example.internet_shop.promotions.dto.UpdatePromotionRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/promotions")
public class PromotionController {

    private final PromotionService promotionService;

    public PromotionController(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    @GetMapping("/")
    public ResponseEntity<List<PromotionResponseDto>> getPromotions() {
        return ResponseEntity.ok(promotionService.getPromotions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PromotionResponseDto> getPromotionById(@PathVariable Long id) {
        return ResponseEntity.ok(promotionService.getPromotionById(id));
    }

    @PostMapping("/")
    public ResponseEntity<PromotionResponseDto> createPromotion(@RequestBody CreatePromotionRequestDto createPromotionRequestDto) {
        PromotionResponseDto createdPromotion = promotionService.createPromotion(createPromotionRequestDto);
        return ResponseEntity.created(URI.create("/api/promotions/" + createdPromotion.getPromotionId())).body(createdPromotion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PromotionResponseDto> updatePromotionById(@PathVariable Long id, @RequestBody UpdatePromotionRequestDto updatePromotionRequestDto) {
        return ResponseEntity.ok(promotionService.updatePromotionById(id, updatePromotionRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePromotionById(@PathVariable Long id) {
        promotionService.deletePromotionById(id);
        return ResponseEntity.ok("Deleted promotion with id " + id);
    }

}
