package com.example.shopberry.domain.complaintimages;

import com.example.shopberry.auth.access.CheckPermission;
import com.example.shopberry.domain.complaintimages.dto.request.AddImageToComplaintRequestDto;
import com.example.shopberry.domain.complaintimages.dto.response.ComplaintImageResponseDto;
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
public class ComplaintImageController {

    private final ComplaintImageService complaintImageService;

    @Operation(summary = "Get all complaint images")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of all complaint images",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ComplaintImageResponseDto.class)
                    )
            )
    })
    @CheckPermission(Permission.COMPLAINT_IMAGE_READ_ALL)
    @GetMapping("/complaints/images")
    public ResponseEntity<List<ComplaintImageResponseDto>> getAllComplaintImages() {
        List<ComplaintImageResponseDto> complaintImageResponseDtoList = complaintImageService.getAllComplaintImages();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(complaintImageResponseDtoList);
    }

    @Operation(summary = "Get complaint's all images")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of complaint's all images",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ComplaintImageResponseDto.class)
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
                    description = "Complaint not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.COMPLAINT_COMPLAINT_IMAGE_READ_ALL)
    @GetMapping("/complaints/{complaintId}/images")
    public ResponseEntity<List<ComplaintImageResponseDto>> getComplaintAllImages(@PathVariable Long complaintId) {
        List<ComplaintImageResponseDto> complaintImageResponseDtoList = complaintImageService.getComplaintAllImages(complaintId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(complaintImageResponseDtoList);
    }

    @Operation(summary = "Get complaint image by id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Complaint image found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ComplaintImageResponseDto.class)
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
                    description = "Complaint image not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.COMPLAINT_IMAGE_READ)
    @GetMapping("/complaints/images/{complaintImageId}")
    public ResponseEntity<ComplaintImageResponseDto> getComplaintImageById(@PathVariable Long complaintImageId) {
        ComplaintImageResponseDto complaintImageResponseDto = complaintImageService.getComplaintImageById(complaintImageId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(complaintImageResponseDto);
    }

    @Operation(summary = "Add image to existing, but not closed complaint")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Image added to complaint successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ComplaintImageResponseDto.class)
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
                    description = "Complaint not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.COMPLAINT_IMAGE_ADD)
    @PostMapping("/complaints/{complaintId}/images")
    public ResponseEntity<ComplaintImageResponseDto> addImageToComplaint(@PathVariable Long complaintId, @Valid @RequestBody AddImageToComplaintRequestDto addImageToComplaintRequestDto) {
        ComplaintImageResponseDto complaintImageResponseDto = complaintImageService.addImageToComplaint(complaintId, addImageToComplaintRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/complaints/" + complaintId + "/images/" + complaintImageResponseDto.getImageId()))
                .body(complaintImageResponseDto);
    }

    @Operation(summary = "Delete complaint's image by id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Complaint's image deleted successfully"
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
                    description = "Complaint image not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.COMPLAINT_IMAGE_DELETE)
    @DeleteMapping("/complaints/images/{complaintImageId}")
    public ResponseEntity<Void> deleteComplaintImageById(@PathVariable Long complaintImageId) {
        complaintImageService.deleteComplaintImageById(complaintImageId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
