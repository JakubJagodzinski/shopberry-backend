package com.example.shopberry.domain.complaintimages;

import com.example.shopberry.common.constants.messages.ComplaintMessages;
import com.example.shopberry.domain.complaintimages.dto.AddImageToComplaintRequestDto;
import com.example.shopberry.domain.complaintimages.dto.ComplaintImageDtoMapper;
import com.example.shopberry.domain.complaintimages.dto.ComplaintImageResponseDto;
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

    public List<ComplaintImageResponseDto> getAllComplaintImages() {
        return complaintImageDtoMapper.toDtoList(complaintImageRepository.findAll());
    }

    @Transactional
    public ComplaintImageResponseDto getComplaintImageById(Long id) throws EntityNotFoundException {
        ComplaintImage complaintImage = complaintImageRepository.findById(id).orElse(null);

        if (complaintImage == null) {
            throw new EntityNotFoundException(ComplaintMessages.COMPLAINT_IMAGE_NOT_FOUND);
        }

        return complaintImageDtoMapper.toDto(complaintImage);
    }

    @Transactional
    public ComplaintImageResponseDto addImageToComplaint(Long complaintId, AddImageToComplaintRequestDto addImageToComplaintRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        Complaint complaint = complaintRepository.findById(complaintId).orElse(null);

        if (complaint == null) {
            throw new EntityNotFoundException(ComplaintMessages.COMPLAINT_NOT_FOUND);
        }

        if (addImageToComplaintRequestDto.getImage() == null) {
            throw new IllegalArgumentException(ComplaintMessages.COMPLAINT_IMAGE_CANNOT_BE_NULL);
        }

        ComplaintImage complaintImage = new ComplaintImage();

        complaintImage.setComplaint(complaint);
        complaintImage.setImage(addImageToComplaintRequestDto.getImage());

        return complaintImageDtoMapper.toDto(complaintImageRepository.save(complaintImage));
    }

    @Transactional
    public void deleteComplaintImageById(Long id) throws EntityNotFoundException {
        if (!complaintImageRepository.existsById(id)) {
            throw new EntityNotFoundException(ComplaintMessages.COMPLAINT_IMAGE_NOT_FOUND);
        }

        complaintImageRepository.deleteById(id);
    }

    @Transactional
    public List<ComplaintImageResponseDto> getComplaintAllImages(Long complaintId) throws EntityNotFoundException {
        if (!complaintRepository.existsById(complaintId)) {
            throw new EntityNotFoundException(ComplaintMessages.COMPLAINT_NOT_FOUND);
        }

        List<ComplaintImage> complaintImages = complaintImageRepository.findAllByComplaint_ComplaintId(complaintId);

        return complaintImageDtoMapper.toDtoList(complaintImages);
    }

}
