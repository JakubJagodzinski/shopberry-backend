package com.example.shopberry.domain.attributes;

import com.example.shopberry.common.MessageResponseDto;
import com.example.shopberry.domain.attributes.dto.AttributeResponseDto;
import com.example.shopberry.domain.attributes.dto.CreateAttributeRequestDto;
import com.example.shopberry.domain.attributes.dto.UpdateAttributeRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/attributes")
@RequiredArgsConstructor
public class AttributeController {

    private final AttributeService attributeService;

    @GetMapping
    public ResponseEntity<List<AttributeResponseDto>> getAttributes() {
        List<AttributeResponseDto> attributeResponseDtoList = attributeService.getAttributes();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(attributeResponseDtoList);
    }

    @GetMapping("/{attributeId}")
    public ResponseEntity<AttributeResponseDto> getAttributeById(@PathVariable Long attributeId) {
        AttributeResponseDto attributeResponseDto = attributeService.getAttributeById(attributeId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(attributeResponseDto);
    }

    @PostMapping
    public ResponseEntity<AttributeResponseDto> createAttribute(@RequestBody CreateAttributeRequestDto createAttributeRequestDto) {
        AttributeResponseDto createdAttributeResponseDto = attributeService.createAttribute(createAttributeRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/attributes/" + createdAttributeResponseDto.getAttributeId()))
                .body(createdAttributeResponseDto);
    }

    @PutMapping("/{attributeId}")
    public ResponseEntity<AttributeResponseDto> updateAttributeById(@PathVariable Long attributeId, @RequestBody UpdateAttributeRequestDto updateAttributeRequestDto) {
        AttributeResponseDto updatedAttributeResponseDto = attributeService.updateAttributeById(attributeId, updateAttributeRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedAttributeResponseDto);
    }

    @DeleteMapping("/{attributeId}")
    public ResponseEntity<MessageResponseDto> deleteAttributeById(@PathVariable Long attributeId) {
        attributeService.deleteAttributeById(attributeId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Attribute with id " + attributeId + " deleted successfully"));
    }

}
