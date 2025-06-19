package com.example.shopberry.domain.complaints;

import com.example.shopberry.common.MessageResponseDto;
import com.example.shopberry.domain.complaints.dto.ComplaintResponseDto;
import com.example.shopberry.domain.complaints.dto.CreateComplaintRequestDto;
import com.example.shopberry.domain.complaints.dto.UpdateComplaintRequestDto;
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

    @GetMapping("/complaints")
    public ResponseEntity<List<ComplaintResponseDto>> getAllComplaints() {
        List<ComplaintResponseDto> complaintResponseDtoList = complaintService.getAllComplaints();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(complaintResponseDtoList);
    }

    @GetMapping("/complaints/{complaintId}")
    public ResponseEntity<ComplaintResponseDto> getComplaintById(@PathVariable Long complaintId) {
        ComplaintResponseDto complaintResponseDto = complaintService.getComplaintById(complaintId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(complaintResponseDto);
    }

    @PostMapping("/complaints")
    public ResponseEntity<ComplaintResponseDto> createComplaint(@RequestBody CreateComplaintRequestDto createComplaintRequestDto) {
        ComplaintResponseDto createdComplaintResponseDto = complaintService.createComplaint(createComplaintRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/complaints/" + createdComplaintResponseDto.getComplaintId()))
                .body(createdComplaintResponseDto);
    }

    @PatchMapping("/complaints/{complaintId}")
    public ResponseEntity<ComplaintResponseDto> updateComplaintById(@PathVariable Long complaintId, @RequestBody UpdateComplaintRequestDto updateComplaintRequestDto) {
        ComplaintResponseDto updatedComplaintResponseDto = complaintService.updateComplaintById(complaintId, updateComplaintRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedComplaintResponseDto);
    }

    @DeleteMapping("/complaints/{complaintId}")
    public ResponseEntity<MessageResponseDto> deleteComplaintById(@PathVariable Long complaintId) {
        complaintService.deleteComplaintById(complaintId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Complaint with id " + complaintId + " deleted successfully"));
    }

}
