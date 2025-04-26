package com.example.internet_shop.promotions;

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
    public ResponseEntity<List<PromotionDto>> getPromotions() {
        return ResponseEntity.ok(promotionService.getPromotions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PromotionDto> getPromotionById(@PathVariable Long id) {
        return ResponseEntity.ok(promotionService.getPromotionById(id));
    }

    @PostMapping("/")
    public ResponseEntity<PromotionDto> createPromotion(@RequestBody CreatePromotionDto createPromotionDto) {
        PromotionDto createdPromotion = promotionService.createPromotion(createPromotionDto);
        return ResponseEntity.created(URI.create("/api/promotions/" + createdPromotion.getPromotionId())).body(createdPromotion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PromotionDto> updatePromotionById(@PathVariable Long id, @RequestBody UpdatePromotionDto updatePromotionDto) {
        return ResponseEntity.ok(promotionService.updatePromotionById(id, updatePromotionDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePromotionById(@PathVariable Long id) {
        promotionService.deletePromotionById(id);
        return ResponseEntity.ok("Deleted promotion with id " + id);
    }

}
