package com.example.shopberry.domain.complaintimages;

import com.example.shopberry.common.MessageResponseDto;
import com.example.shopberry.domain.complaintimages.dto.AddImageToComplaintRequestDto;
import com.example.shopberry.domain.complaintimages.dto.ComplaintImageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ComplaintImageController {

    private final ComplaintImageService complaintImageService;

    @GetMapping("/complaints/images")
    public ResponseEntity<List<ComplaintImageResponseDto>> getAllComplaintImages() {
        List<ComplaintImageResponseDto> complaintImageResponseDtoList = complaintImageService.getAllComplaintImages();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(complaintImageResponseDtoList);
    }

    @GetMapping("/complaints/images/{complaintImageId}")
    public ResponseEntity<ComplaintImageResponseDto> getComplaintImageById(@PathVariable Long complaintImageId) {
        ComplaintImageResponseDto complaintImageResponseDto = complaintImageService.getComplaintImageById(complaintImageId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(complaintImageResponseDto);
    }

    @PostMapping("/complaints/{complaintId}/images")
    public ResponseEntity<ComplaintImageResponseDto> addImageToComplaint(@PathVariable Long complaintId, @RequestBody AddImageToComplaintRequestDto addImageToComplaintRequestDto) {
        ComplaintImageResponseDto complaintImageResponseDto = complaintImageService.addImageToComplaint(complaintId, addImageToComplaintRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/complaints/" + complaintId + "/images/" + complaintImageResponseDto.getId()))
                .body(complaintImageResponseDto);
    }

    @DeleteMapping("/complaints/images/{complaintImageId}")
    public ResponseEntity<MessageResponseDto> deleteComplaintImageById(@PathVariable Long complaintImageId) {
        complaintImageService.deleteComplaintImageById(complaintImageId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Complaint image with id " + complaintImageId + " deleted successfully"));
    }

}
