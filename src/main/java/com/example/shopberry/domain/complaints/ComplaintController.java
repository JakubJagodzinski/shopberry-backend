package com.example.shopberry.domain.complaints;

import com.example.shopberry.auth.access.CheckPermission;
import com.example.shopberry.common.MessageResponseDto;
import com.example.shopberry.domain.complaints.dto.ComplaintResponseDto;
import com.example.shopberry.domain.complaints.dto.CreateComplaintRequestDto;
import com.example.shopberry.domain.complaints.dto.UpdateComplaintRequestDto;
import com.example.shopberry.user.Permission;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
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
    public ResponseEntity<ComplaintResponseDto> createComplaint(@RequestBody CreateComplaintRequestDto createComplaintRequestDto) {
        ComplaintResponseDto createdComplaintResponseDto = complaintService.createComplaint(createComplaintRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/complaints/" + createdComplaintResponseDto.getComplaintId()))
                .body(createdComplaintResponseDto);
    }

    @CheckPermission(Permission.COMPLAINT_UPDATE)
    @PatchMapping("/complaints/{complaintId}")
    public ResponseEntity<ComplaintResponseDto> updateComplaintById(@PathVariable Long complaintId, @RequestBody UpdateComplaintRequestDto updateComplaintRequestDto) {
        ComplaintResponseDto updatedComplaintResponseDto = complaintService.updateComplaintById(complaintId, updateComplaintRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedComplaintResponseDto);
    }

    @CheckPermission(Permission.COMPLAINT_DELETE)
    @DeleteMapping("/complaints/{complaintId}")
    public ResponseEntity<MessageResponseDto> deleteComplaintById(@PathVariable Long complaintId) {
        complaintService.deleteComplaintById(complaintId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Complaint with id " + complaintId + " deleted successfully"));
    }

}
