package com.example.internet_shop.attributes;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/attributes")
public class AttributeController {

    private final AttributeService attributeService;

    public AttributeController(AttributeService attributeService) {
        this.attributeService = attributeService;
    }

    @GetMapping("/")
    public ResponseEntity<List<AttributeDto>> getAttributes() {
        return ResponseEntity.ok(attributeService.getAttributes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttributeDto> getAttributeById(@PathVariable Long id) {
        return ResponseEntity.ok(attributeService.getAttributeById(id));
    }

    @PostMapping("/")
    public ResponseEntity<AttributeDto> createAttribute(@RequestBody CreateAttributeDto createAttributeDto) {
        AttributeDto createdAttribute = attributeService.createAttribute(createAttributeDto);

        return ResponseEntity.created(URI.create("api/attributes/" + createdAttribute.getAttributeId())).body(createdAttribute);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AttributeDto> updateAttributeById(@PathVariable Long id, @RequestBody UpdateAttributeDto updateAttributeDto) {
        return ResponseEntity.ok(attributeService.updateAttributeById(id, updateAttributeDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAttributeById(@PathVariable Long id) {
        attributeService.deleteAttributeById(id);

        return ResponseEntity.ok("Deleted attribute with id " + id);
    }

}
