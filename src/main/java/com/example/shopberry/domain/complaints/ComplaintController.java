package com.example.shopberry.domain.complaints;

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
@RequestMapping("/api/v1/complaints")
@RequiredArgsConstructor
public class ComplaintController {

    private final ComplaintService complaintService;

    @GetMapping("/")
    public ResponseEntity<List<ComplaintResponseDto>> getComplaints() {
        List<ComplaintResponseDto> complaintResponseDtoList = complaintService.getComplaints();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(complaintResponseDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComplaintResponseDto> getComplaintById(@PathVariable Long id) {
        ComplaintResponseDto complaintResponseDto = complaintService.getComplaintById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(complaintResponseDto);
    }

    @PostMapping("/")
    public ResponseEntity<ComplaintResponseDto> createComplaint(@RequestBody CreateComplaintRequestDto createComplaintRequestDto) {
        ComplaintResponseDto createdComplaintResponseDto = complaintService.createComplaint(createComplaintRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/complaints/" + createdComplaintResponseDto.getComplaintId()))
                .body(createdComplaintResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComplaintResponseDto> updateComplaintById(@PathVariable Long id, @RequestBody UpdateComplaintRequestDto updateComplaintRequestDto) {
        ComplaintResponseDto updatedComplaintResponseDto = complaintService.updateComplaintById(id, updateComplaintRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedComplaintResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComplaintById(@PathVariable Long id) {
        complaintService.deleteComplaintById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Complaint with id " + id + " deleted successfully");
    }

}
