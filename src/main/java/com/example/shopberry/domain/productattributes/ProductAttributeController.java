package com.example.shopberry.domain.productattributes;

import com.example.shopberry.auth.access.CheckPermission;
import com.example.shopberry.common.MessageResponseDto;
import com.example.shopberry.domain.productattributes.dto.AssignAttributeToProductRequestDto;
import com.example.shopberry.domain.productattributes.dto.ProductAttributeResponseDto;
import com.example.shopberry.domain.productattributes.dto.UpdateProductAttributeRequestDto;
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
public class ProductAttributeController {

    private final ProductAttributeService productAttributeService;

    @GetMapping("/products/{productId}/attributes")
    public ResponseEntity<List<ProductAttributeResponseDto>> getProductAllAttributes(@PathVariable Long productId) {
        List<ProductAttributeResponseDto> productAttributeResponseDtoList = productAttributeService.getProductAllAttributes(productId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productAttributeResponseDtoList);
    }

    @GetMapping("/products/attributes/{attributeId}")
    public ResponseEntity<List<ProductAttributeResponseDto>> getAllProductsWithAttribute(@PathVariable Long attributeId) {
        List<ProductAttributeResponseDto> productAttributeResponseDtoList = productAttributeService.getAllProductsWithAttribute(attributeId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productAttributeResponseDtoList);
    }

    @CheckPermission(Permission.PRODUCT_ATTRIBUTE_ASSIGN)
    @PostMapping("/products/{productId}/attributes")
    public ResponseEntity<ProductAttributeResponseDto> assignAttributeToProduct(@PathVariable Long productId, @Valid @RequestBody AssignAttributeToProductRequestDto assignAttributeToProductRequestDto) {
        ProductAttributeResponseDto createdProductAttributeResponseDto = productAttributeService.assignAttributeToProduct(productId, assignAttributeToProductRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/products/" + productId + "/attributes/" + createdProductAttributeResponseDto.getAttribute().getAttributeId()))
                .body(createdProductAttributeResponseDto);
    }

    @CheckPermission(Permission.PRODUCT_ATTRIBUTE_UPDATE)
    @PatchMapping("/products/{productId}/attributes/{attributeId}")
    public ResponseEntity<ProductAttributeResponseDto> updateProductAttributeById(@PathVariable Long productId, @PathVariable Long attributeId, @Valid @RequestBody UpdateProductAttributeRequestDto updateProductAttributeRequestDto) {
        ProductAttributeResponseDto updatedProductAttributeResponseDto = productAttributeService.updateProductAttributeById(productId, attributeId, updateProductAttributeRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedProductAttributeResponseDto);
    }

    @CheckPermission(Permission.PRODUCT_ATTRIBUTE_UNASSIGN)
    @DeleteMapping("/products/{productId}/attributes/{attributeId}")
    public ResponseEntity<MessageResponseDto> unassignAttributeFromProduct(@PathVariable Long productId, @PathVariable Long attributeId) {
        productAttributeService.unassignAttributeFromProduct(productId, attributeId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Attribute with id " + attributeId + " unassigned from product with id " + productId + " successfully"));
    }

}
