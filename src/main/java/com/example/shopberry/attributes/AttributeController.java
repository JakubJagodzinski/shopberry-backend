package com.example.shopberry.attributes;

import com.example.shopberry.attributes.dto.AttributeResponseDto;
import com.example.shopberry.attributes.dto.CreateAttributeRequestDto;
import com.example.shopberry.attributes.dto.UpdateAttributeRequestDto;
import org.springframework.http.HttpStatus;
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
        List<AttributeResponseDto> attributeResponseDtoList = attributeService.getAttributes();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(attributeResponseDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttributeResponseDto> getAttributeById(@PathVariable Long id) {
        AttributeResponseDto attributeResponseDto = attributeService.getAttributeById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(attributeResponseDto);
    }

    @PostMapping("/")
    public ResponseEntity<AttributeResponseDto> createAttribute(@RequestBody CreateAttributeRequestDto createAttributeRequestDto) {
        AttributeResponseDto createdAttributeResponseDto = attributeService.createAttribute(createAttributeRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/attributes/" + createdAttributeResponseDto.getAttributeId()))
                .body(createdAttributeResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AttributeResponseDto> updateAttributeById(@PathVariable Long id, @RequestBody UpdateAttributeRequestDto updateAttributeRequestDto) {
        AttributeResponseDto updatedAttributeResponseDto = attributeService.updateAttributeById(id, updateAttributeRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedAttributeResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAttributeById(@PathVariable Long id) {
        attributeService.deleteAttributeById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Attribute with id " + id + " deleted successfully");
    }

}
