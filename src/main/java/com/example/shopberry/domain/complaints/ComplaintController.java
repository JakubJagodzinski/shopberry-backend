package com.example.shopberry.domain.complaints;

import com.example.shopberry.auth.access.CheckPermission;
import com.example.shopberry.domain.complaints.dto.request.CreateComplaintRequestDto;
import com.example.shopberry.domain.complaints.dto.request.UpdateComplaintRequestDto;
import com.example.shopberry.domain.complaints.dto.response.ComplaintResponseDto;
import com.example.shopberry.user.Permission;
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

    @CheckPermission(Permission.COMPLAINT_READ_ALL)
    @GetMapping("/complaints")
    public ResponseEntity<List<ComplaintResponseDto>> getAllComplaints() {
        List<ComplaintResponseDto> complaintResponseDtoList = complaintService.getAllComplaints();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(complaintResponseDtoList);
    }

    @CheckPermission(Permission.CUSTOMER_COMPLAINT_READ_ALL)
    @GetMapping("/customers/{customerId}/complaints")
    public ResponseEntity<List<ComplaintResponseDto>> getCustomerAllComplaints(@PathVariable UUID customerId) {
        List<ComplaintResponseDto> complaintResponseDtoList = complaintService.getCustomerAllComplaints(customerId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(complaintResponseDtoList);
    }

    @CheckPermission(Permission.COMPLAINT_READ)
    @GetMapping("/complaints/{complaintId}")
    public ResponseEntity<ComplaintResponseDto> getComplaintById(@PathVariable Long complaintId) {
        ComplaintResponseDto complaintResponseDto = complaintService.getComplaintById(complaintId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(complaintResponseDto);
    }

    @CheckPermission(Permission.COMPLAINT_CREATE)
    @PostMapping("/complaints")
    public ResponseEntity<ComplaintResponseDto> createComplaint(@Valid @RequestBody CreateComplaintRequestDto createComplaintRequestDto) {
        ComplaintResponseDto createdComplaintResponseDto = complaintService.createComplaint(createComplaintRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/complaints/" + createdComplaintResponseDto.getComplaintId()))
                .body(createdComplaintResponseDto);
    }

    @CheckPermission(Permission.COMPLAINT_UPDATE)
    @PatchMapping("/complaints/{complaintId}")
    public ResponseEntity<ComplaintResponseDto> updateComplaintById(@PathVariable Long complaintId, @Valid @RequestBody UpdateComplaintRequestDto updateComplaintRequestDto) {
        ComplaintResponseDto updatedComplaintResponseDto = complaintService.updateComplaintById(complaintId, updateComplaintRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedComplaintResponseDto);
    }

    @CheckPermission(Permission.COMPLAINT_DELETE)
    @DeleteMapping("/complaints/{complaintId}")
    public ResponseEntity<Void> deleteComplaintById(@PathVariable Long complaintId) {
        complaintService.deleteComplaintById(complaintId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
