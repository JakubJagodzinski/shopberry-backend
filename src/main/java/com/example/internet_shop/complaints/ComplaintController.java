package com.example.internet_shop.complaints;

import com.example.internet_shop.complaints.dto.ComplaintResponseDto;
import com.example.internet_shop.complaints.dto.CreateComplaintRequestDto;
import com.example.internet_shop.complaints.dto.UpdateComplaintRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/complaints")
public class ComplaintController {

    private final ComplaintService complaintService;

    public ComplaintController(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    @GetMapping("/")
    public ResponseEntity<List<ComplaintResponseDto>> getComplaints() {
        return ResponseEntity.ok(complaintService.getComplaints());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComplaintResponseDto> getComplaintById(@PathVariable Long id) {
        return ResponseEntity.ok(complaintService.getComplaintById(id));
    }

    @PostMapping("/")
    public ResponseEntity<ComplaintResponseDto> createComplaint(@RequestBody CreateComplaintRequestDto createComplaintRequestDto) {
        ComplaintResponseDto createdComplaint = complaintService.createComplaint(createComplaintRequestDto);

        return ResponseEntity.created(URI.create("/api/complaints/" + createdComplaint.getComplaintId())).body(createdComplaint);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComplaintResponseDto> updateComplaintById(@PathVariable Long id, @RequestBody UpdateComplaintRequestDto updateComplaintRequestDto) {
        return ResponseEntity.ok(complaintService.updateComplaintById(id, updateComplaintRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComplaintById(@PathVariable Long id) {
        complaintService.deleteComplaintById(id);

        return ResponseEntity.noContent().build();
    }

}
