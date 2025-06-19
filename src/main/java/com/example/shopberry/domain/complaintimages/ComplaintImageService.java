package com.example.shopberry.domain.complaintimages;

import com.example.shopberry.domain.complaintimages.dto.ComplaintImageDtoMapper;
import com.example.shopberry.domain.complaintimages.dto.ComplaintImageResponseDto;
import com.example.shopberry.domain.complaintimages.dto.AddImageToComplaintRequestDto;
import com.example.shopberry.domain.complaints.Complaint;
import com.example.shopberry.domain.complaints.ComplaintRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComplaintImageService {

    private final ComplaintImageRepository complaintImageRepository;
    private final ComplaintRepository complaintRepository;

    private final ComplaintImageDtoMapper complaintImageDtoMapper;

    private static final String COMPLAINT_IMAGE_NOT_FOUND_MESSAGE = "Complaint image not found";
    private static final String COMPLAINT_DOES_NOT_EXIST_MESSAGE = "Complaint does not exist";
    private static final String COMPLAINT_IMAGE_CANNOT_BE_NULL_MESSAGE = "Complaint image cannot be null";

    public List<ComplaintImageResponseDto> getAllComplaintImages() {
        return complaintImageDtoMapper.toDtoList(complaintImageRepository.findAll());
    }

    @Transactional
    public ComplaintImageResponseDto getComplaintImageById(Long id) throws EntityNotFoundException {
        ComplaintImage complaintImage = complaintImageRepository.findById(id).orElse(null);

        if (complaintImage == null) {
            throw new EntityNotFoundException(COMPLAINT_IMAGE_NOT_FOUND_MESSAGE);
        }

        return complaintImageDtoMapper.toDto(complaintImage);
    }

    @Transactional
    public ComplaintImageResponseDto addImageToComplaint(Long complaintId, AddImageToComplaintRequestDto addImageToComplaintRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        Complaint complaint = complaintRepository.findById(complaintId).orElse(null);

        if (complaint == null) {
            throw new EntityNotFoundException(COMPLAINT_DOES_NOT_EXIST_MESSAGE);
        }

        if (addImageToComplaintRequestDto.getImage() == null) {
            throw new IllegalArgumentException(COMPLAINT_IMAGE_CANNOT_BE_NULL_MESSAGE);
        }

        ComplaintImage complaintImage = new ComplaintImage();

        complaintImage.setComplaint(complaint);
        complaintImage.setImage(addImageToComplaintRequestDto.getImage());

        return complaintImageDtoMapper.toDto(complaintImageRepository.save(complaintImage));
    }

    @Transactional
    public void deleteComplaintImageById(Long id) throws EntityNotFoundException {
        if (!complaintImageRepository.existsById(id)) {
            throw new EntityNotFoundException(COMPLAINT_IMAGE_NOT_FOUND_MESSAGE);
        }

        complaintImageRepository.deleteById(id);
    }

}
