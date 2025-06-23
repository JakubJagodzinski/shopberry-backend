package com.example.shopberry.domain.producers;

import com.example.shopberry.auth.access.CheckPermission;
import com.example.shopberry.domain.producers.dto.request.CreateProducerRequestDto;
import com.example.shopberry.domain.producers.dto.request.UpdateProducerRequestDto;
import com.example.shopberry.domain.producers.dto.response.ProducerResponseDto;
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
public class ProducerController {

    private final ProducerService producerService;

    @Operation(summary = "Get all producers")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of producers",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProducerResponseDto.class)
                    )
            )
    })
    @GetMapping("/producers")
    public ResponseEntity<List<ProducerResponseDto>> getAllProducers() {
        List<ProducerResponseDto> producerResponseDtoList = producerService.getAllProducers();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(producerResponseDtoList);
    }

    @Operation(summary = "Get producer by id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Producer found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProducerResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Producer not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @GetMapping("/producers/{producerId}")
    public ResponseEntity<ProducerResponseDto> getProducerById(@PathVariable Long producerId) {
        ProducerResponseDto producerResponseDto = producerService.getProducerById(producerId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(producerResponseDto);
    }

    @Operation(summary = "Create a new producer")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Producer created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProducerResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Producer with that name already exists",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access Denied",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.PRODUCER_CREATE)
    @PostMapping("/producers")
    public ResponseEntity<ProducerResponseDto> createProducer(@Valid @RequestBody CreateProducerRequestDto createProducerRequestDto) {
        ProducerResponseDto createdProducerResponseDto = producerService.createProducer(createProducerRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/producers/" + createdProducerResponseDto.getProducerId()))
                .body(createdProducerResponseDto);
    }

    @Operation(summary = "Update producer by ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Producer updated",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProducerResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Producer with that name already exists",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access Denied",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Producer not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.PRODUCER_UPDATE)
    @PatchMapping("/producers/{producerId}")
    public ResponseEntity<ProducerResponseDto> updateProducerById(@Valid @PathVariable Long producerId, @RequestBody UpdateProducerRequestDto updateProducerRequestDto) {
        ProducerResponseDto updatedProducerResponseDto = producerService.updateProducerById(producerId, updateProducerRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedProducerResponseDto);
    }

    @Operation(summary = "Delete producer by ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Producer deleted"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access Denied",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Producer not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.PRODUCER_DELETE)
    @DeleteMapping("/producers/{producerId}")
    public ResponseEntity<Void> deleteProducerById(@PathVariable Long producerId) {
        producerService.deleteProducerById(producerId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
