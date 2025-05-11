package com.example.internet_shop.complaintimages;

import com.example.internet_shop.complaintimages.dto.ComplaintImageResponseDto;
import com.example.internet_shop.complaintimages.dto.CreateComplaintImageRequestDto;
import org.springframework.http.HttpStatus;
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
        List<ComplaintImageResponseDto> complaintImageResponseDtoList = complaintImageService.getComplaintImages();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(complaintImageResponseDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComplaintImageResponseDto> getComplaintImageById(@PathVariable Long id) {
        ComplaintImageResponseDto complaintImageResponseDto = complaintImageService.getComplaintImageById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(complaintImageResponseDto);
    }

    @PostMapping("/")
    public ResponseEntity<ComplaintImageResponseDto> createComplaintImage(@RequestBody CreateComplaintImageRequestDto createComplaintImageRequestDto) {
        ComplaintImageResponseDto createdComplaintImageResponseDto = complaintImageService.createComplaintImage(createComplaintImageRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/complaint-images/" + createdComplaintImageResponseDto.getId()))
                .body(createdComplaintImageResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComplaintImageById(@PathVariable Long id) {
        complaintImageService.deleteComplaintImageById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Complaint image with id " + id + " deleted successfully");
    }

}
