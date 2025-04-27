package com.example.internet_shop.complaintimages;

import com.example.internet_shop.complaints.ComplaintRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplaintImageService {

    private final ComplaintImageRepository complaintImageRepository;
    private final ComplaintRepository complaintRepository;

    private final ComplaintImageDtoMapper complaintImageDtoMapper;

    private final String COMPLAINT_IMAGE_NOT_FOUND_MESSAGE = "Complaint image not found";
    private final String COMPLAINT_DOES_NOT_EXIST_MESSAGE = "Complaint does not exist";
    private final String COMPLAINT_IMAGE_CANNOT_BE_NULL_MESSAGE = "Complaint image cannot be null";

    public ComplaintImageService(ComplaintImageRepository complaintImageRepository, ComplaintRepository complaintRepository, ComplaintImageDtoMapper complaintImageDtoMapper) {
        this.complaintImageRepository = complaintImageRepository;
        this.complaintRepository = complaintRepository;
        this.complaintImageDtoMapper = complaintImageDtoMapper;
    }

    public List<ComplaintImageDto> getComplaintImages() {
        return complaintImageDtoMapper.toDtoList(complaintImageRepository.findAll());
    }

    @Transactional
    public ComplaintImageDto getComplaintImageById(Long id) throws EntityNotFoundException {
        if (!complaintImageRepository.existsById(id)) {
            throw new EntityNotFoundException(COMPLAINT_IMAGE_NOT_FOUND_MESSAGE);
        }

        return complaintImageDtoMapper.toDto(complaintImageRepository.getReferenceById(id));
    }

    @Transactional
    public ComplaintImageDto createComplaintImage(CreateComplaintImageDto createComplaintImageDto) throws EntityNotFoundException, IllegalArgumentException {
        if (!complaintRepository.existsById(createComplaintImageDto.getComplaintId())) {
            throw new EntityNotFoundException(COMPLAINT_DOES_NOT_EXIST_MESSAGE);
        }

        if (createComplaintImageDto.getImage() == null) {
            throw new IllegalArgumentException(COMPLAINT_IMAGE_CANNOT_BE_NULL_MESSAGE);
        }

        ComplaintImage complaintImage = new ComplaintImage();

        complaintImage.setComplaint(complaintRepository.getReferenceById(createComplaintImageDto.getComplaintId()));
        complaintImage.setImage(createComplaintImageDto.getImage());

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
