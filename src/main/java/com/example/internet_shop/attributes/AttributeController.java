package com.example.internet_shop.attributes;

import com.example.internet_shop.attributes.dto.AttributeResponseDto;
import com.example.internet_shop.attributes.dto.CreateAttributeRequestDto;
import com.example.internet_shop.attributes.dto.UpdateAttributeRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/attributes")
public class AttributeController {

    private final AttributeService attributeService;

    public AttributeController(AttributeService attributeService) {
        this.attributeService = attributeService;
    }

    @GetMapping("/")
    public ResponseEntity<List<AttributeResponseDto>> getAttributes() {
        return ResponseEntity.ok(attributeService.getAttributes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttributeResponseDto> getAttributeById(@PathVariable Long id) {
        return ResponseEntity.ok(attributeService.getAttributeById(id));
    }

    @PostMapping("/")
    public ResponseEntity<AttributeResponseDto> createAttribute(@RequestBody CreateAttributeRequestDto createAttributeRequestDto) {
        AttributeResponseDto createdAttribute = attributeService.createAttribute(createAttributeRequestDto);

        return ResponseEntity.created(URI.create("/api/v1/attributes/" + createdAttribute.getAttributeId())).body(createdAttribute);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AttributeResponseDto> updateAttributeById(@PathVariable Long id, @RequestBody UpdateAttributeRequestDto updateAttributeRequestDto) {
        return ResponseEntity.ok(attributeService.updateAttributeById(id, updateAttributeRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAttributeById(@PathVariable Long id) {
        attributeService.deleteAttributeById(id);

        return ResponseEntity.ok("Deleted attribute with id " + id);
    }

}
