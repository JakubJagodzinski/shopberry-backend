package com.example.shopberry.domain.attributes;

import com.example.shopberry.auth.access.CheckPermission;
import com.example.shopberry.domain.attributes.dto.request.CreateAttributeRequestDto;
import com.example.shopberry.domain.attributes.dto.request.UpdateAttributeRequestDto;
import com.example.shopberry.domain.attributes.dto.response.AttributeResponseDto;
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
public class AttributeController {

    private final AttributeService attributeService;

    @Operation(summary = "Get all attributes")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of attributes",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AttributeResponseDto.class)
                    )
            )
    })
    @GetMapping("/attributes")
    public ResponseEntity<List<AttributeResponseDto>> getAllAttributes() {
        List<AttributeResponseDto> attributeResponseDtoList = attributeService.getAllAttributes();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(attributeResponseDtoList);
    }

    @Operation(summary = "Get attribute by ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Attribute found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AttributeResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Attribute not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @GetMapping("/attributes/{attributeId}")
    public ResponseEntity<AttributeResponseDto> getAttributeById(@PathVariable Long attributeId) {
        AttributeResponseDto attributeResponseDto = attributeService.getAttributeById(attributeId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(attributeResponseDto);
    }

    @Operation(summary = "Create new attribute")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Attribute created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AttributeResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request data",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access denied",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.ATTRIBUTE_CREATE)
    @PostMapping("/attributes")
    public ResponseEntity<AttributeResponseDto> createAttribute(@Valid @RequestBody CreateAttributeRequestDto createAttributeRequestDto) {
        AttributeResponseDto createdAttributeResponseDto = attributeService.createAttribute(createAttributeRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/attributes/" + createdAttributeResponseDto.getAttributeId()))
                .body(createdAttributeResponseDto);
    }

    @Operation(summary = "Update attribute by ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Attribute updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AttributeResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request data",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access denied",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Attribute not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.ATTRIBUTE_UPDATE)
    @PatchMapping("/attributes/{attributeId}")
    public ResponseEntity<AttributeResponseDto> updateAttributeById(@PathVariable Long attributeId, @Valid @RequestBody UpdateAttributeRequestDto updateAttributeRequestDto) {
        AttributeResponseDto updatedAttributeResponseDto = attributeService.updateAttributeById(attributeId, updateAttributeRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedAttributeResponseDto);
    }

    @Operation(summary = "Delete attribute by ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Attribute deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access denied",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Attribute not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.ATTRIBUTE_DELETE)
    @DeleteMapping("/attributes/{attributeId}")
    public ResponseEntity<Void> deleteAttributeById(@PathVariable Long attributeId) {
        attributeService.deleteAttributeById(attributeId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
