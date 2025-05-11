package com.example.internet_shop.complaintimages;

import com.example.internet_shop.complaintimages.dto.ComplaintImageResponseDto;
import com.example.internet_shop.complaintimages.dto.CreateComplaintImageRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/complaint-images")
public class ComplaintImageController {

    private final ComplaintImageService complaintImageService;

    public ComplaintImageController(ComplaintImageService complaintImageService) {
        this.complaintImageService = complaintImageService;
    }

    @GetMapping("/")
    public ResponseEntity<List<ComplaintImageResponseDto>> getComplaintImages() {
        return ResponseEntity.ok(complaintImageService.getComplaintImages());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComplaintImageResponseDto> getComplaintImageById(@PathVariable Long id) {
        return ResponseEntity.ok(complaintImageService.getComplaintImageById(id));
    }

    @PostMapping("/")
    public ResponseEntity<ComplaintImageResponseDto> createComplaintImage(@RequestBody CreateComplaintImageRequestDto createComplaintImageRequestDto) {
        ComplaintImageResponseDto createdComplaintImage = complaintImageService.createComplaintImage(createComplaintImageRequestDto);

        return ResponseEntity.created(URI.create("/api/v1/complaint-images/" + createdComplaintImage.getId())).body(createdComplaintImage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComplaintImageById(@PathVariable Long id) {
        complaintImageService.deleteComplaintImageById(id);

        return ResponseEntity.ok("Deleted complaint image with id: " + id);
    }

}
