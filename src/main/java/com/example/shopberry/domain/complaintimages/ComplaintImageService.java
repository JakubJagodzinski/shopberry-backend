package com.example.shopberry.domain.complaintimages;

import com.example.shopberry.auth.access.manager.ComplaintImageAccessManager;
import com.example.shopberry.common.constants.messages.ComplaintMessages;
import com.example.shopberry.domain.complaintimages.dto.ComplaintImageDtoMapper;
import com.example.shopberry.domain.complaintimages.dto.request.AddImageToComplaintRequestDto;
import com.example.shopberry.domain.complaintimages.dto.response.ComplaintImageResponseDto;
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

    private final ComplaintImageAccessManager complaintImageAccessManager;

    public List<ComplaintImageResponseDto> getAllComplaintImages() {
        return complaintImageDtoMapper.toDtoList(complaintImageRepository.findAll());
    }

    @Transactional
    public ComplaintImageResponseDto getComplaintImageById(Long imageId) throws EntityNotFoundException {
        ComplaintImage complaintImage = complaintImageRepository.findById(imageId).orElse(null);

        if (complaintImage == null) {
            throw new EntityNotFoundException(ComplaintMessages.COMPLAINT_IMAGE_NOT_FOUND);
        }

        complaintImageAccessManager.checkCanReadComplaintImage(complaintImage);

        return complaintImageDtoMapper.toDto(complaintImage);
    }

    @Transactional
    public List<ComplaintImageResponseDto> getComplaintAllImages(Long complaintId) throws EntityNotFoundException {
        Complaint complaint = complaintRepository.findById(complaintId).orElse(null);

        if (complaint == null) {
            throw new EntityNotFoundException(ComplaintMessages.COMPLAINT_NOT_FOUND);
        }

        complaintImageAccessManager.checkCanReadComplaintAllImages(complaint);

        List<ComplaintImage> complaintImages = complaintImageRepository.findAllByComplaint_ComplaintId(complaintId);

        return complaintImageDtoMapper.toDtoList(complaintImages);
    }

    @Transactional
    public ComplaintImageResponseDto addImageToComplaint(Long complaintId, AddImageToComplaintRequestDto addImageToComplaintRequestDto) throws EntityNotFoundException {
        Complaint complaint = complaintRepository.findById(complaintId).orElse(null);

        if (complaint == null) {
            throw new EntityNotFoundException(ComplaintMessages.COMPLAINT_NOT_FOUND);
        }

        complaintImageAccessManager.checkCanAddImageToComplaint(complaint);

        ComplaintImage complaintImage = new ComplaintImage();

        complaintImage.setComplaint(complaint);
        complaintImage.setImage(addImageToComplaintRequestDto.getImage());

        return complaintImageDtoMapper.toDto(complaintImageRepository.save(complaintImage));
    }

    @Transactional
    public void deleteComplaintImageById(Long imageId) throws EntityNotFoundException {
        ComplaintImage complaintImage = complaintImageRepository.findById(imageId).orElse(null);

        if (complaintImage == null) {
            throw new EntityNotFoundException(ComplaintMessages.COMPLAINT_IMAGE_NOT_FOUND);
        }

        complaintImageAccessManager.checkCanDeleteComplaintImage(complaintImage);

        complaintImageRepository.deleteById(imageId);
    }

}
