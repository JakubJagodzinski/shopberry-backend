package com.example.internet_shop.complaints;

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
    public ResponseEntity<List<ComplaintDto>> getComplaints() {
        return ResponseEntity.ok(complaintService.getComplaints());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComplaintDto> getComplaintById(@PathVariable Long id) {
        return ResponseEntity.ok(complaintService.getComplaintById(id));
    }

    @PostMapping("/")
    public ResponseEntity<ComplaintDto> createComplaint(@RequestBody CreateComplaintDto createComplaintDto) {
        ComplaintDto createdComplaint = complaintService.createComplaint(createComplaintDto);

        return ResponseEntity.created(URI.create("/api/complaints/" + createdComplaint.getComplaintId())).body(createdComplaint);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComplaintDto> updateComplaintById(@PathVariable Long id, @RequestBody UpdateComplaintDto updateComplaintDto) {
        return ResponseEntity.ok(complaintService.updateComplaintById(id, updateComplaintDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComplaintById(@PathVariable Long id) {
        complaintService.deleteComplaintById(id);

        return ResponseEntity.noContent().build();
    }

}
