package com.example.shopberry.domain.complaints;

import com.example.shopberry.auth.access.CheckPermission;
import com.example.shopberry.domain.complaints.dto.request.CreateComplaintRequestDto;
import com.example.shopberry.domain.complaints.dto.request.UpdateComplaintRequestDto;
import com.example.shopberry.domain.complaints.dto.response.ComplaintResponseDto;
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
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
public class ComplaintController {

    private final ComplaintService complaintService;

    @Operation(summary = "Get all complaints")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of complaints",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ComplaintResponseDto.class)
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
    @CheckPermission(Permission.COMPLAINT_READ_ALL)
    @GetMapping("/complaints")
    public ResponseEntity<List<ComplaintResponseDto>> getAllComplaints() {
        List<ComplaintResponseDto> complaintResponseDtoList = complaintService.getAllComplaints();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(complaintResponseDtoList);
    }

    @Operation(summary = "Get customer's all complaints")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of customer's complaints",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ComplaintResponseDto.class)
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
                    description = "Customer not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.CUSTOMER_COMPLAINT_READ_ALL)
    @GetMapping("/customers/{customerId}/complaints")
    public ResponseEntity<List<ComplaintResponseDto>> getCustomerAllComplaints(@PathVariable UUID customerId) {
        List<ComplaintResponseDto> complaintResponseDtoList = complaintService.getCustomerAllComplaints(customerId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(complaintResponseDtoList);
    }

    @Operation(summary = "Get complaint by id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Complaint found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ComplaintResponseDto.class)
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
    @CheckPermission(Permission.COMPLAINT_READ)
    @GetMapping("/complaints/{complaintId}")
    public ResponseEntity<ComplaintResponseDto> getComplaintById(@PathVariable Long complaintId) {
        ComplaintResponseDto complaintResponseDto = complaintService.getComplaintById(complaintId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(complaintResponseDto);
    }

    @Operation(summary = "Create new complaint for the order")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "New complaint created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ComplaintResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Product doesn't belong to that order",
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
                    description = "Order / product not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.COMPLAINT_CREATE)
    @PostMapping("/complaints")
    public ResponseEntity<ComplaintResponseDto> createComplaint(@Valid @RequestBody CreateComplaintRequestDto createComplaintRequestDto) {
        ComplaintResponseDto createdComplaintResponseDto = complaintService.createComplaint(createComplaintRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/complaints/" + createdComplaintResponseDto.getComplaintId()))
                .body(createdComplaintResponseDto);
    }

    @Operation(summary = "Update complaint information")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Complaint updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ComplaintResponseDto.class)
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
    @CheckPermission(Permission.COMPLAINT_UPDATE)
    @PatchMapping("/complaints/{complaintId}")
    public ResponseEntity<ComplaintResponseDto> updateComplaintById(@PathVariable Long complaintId, @Valid @RequestBody UpdateComplaintRequestDto updateComplaintRequestDto) {
        ComplaintResponseDto updatedComplaintResponseDto = complaintService.updateComplaintById(complaintId, updateComplaintRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedComplaintResponseDto);
    }

    @Operation(summary = "Delete complaint by id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Complaint deleted successfully"
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
    @CheckPermission(Permission.COMPLAINT_DELETE)
    @DeleteMapping("/complaints/{complaintId}")
    public ResponseEntity<Void> deleteComplaintById(@PathVariable Long complaintId) {
        complaintService.deleteComplaintById(complaintId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
