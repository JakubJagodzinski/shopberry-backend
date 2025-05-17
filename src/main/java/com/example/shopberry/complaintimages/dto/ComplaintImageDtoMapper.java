package com.example.shopberry.complaintimages.dto;


import com.example.shopberry.complaintimages.ComplaintImage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ComplaintImageDtoMapper {

    public ComplaintImageResponseDto toDto(ComplaintImage ComplaintImage) {
        ComplaintImageResponseDto ComplaintImageResponseDto = new ComplaintImageResponseDto();

        ComplaintImageResponseDto.setId(ComplaintImage.getId());
        ComplaintImageResponseDto.setImage(ComplaintImage.getImage());

        return ComplaintImageResponseDto;
    }

    public List<ComplaintImageResponseDto> toDtoList(List<ComplaintImage> ComplaintImages) {
        return ComplaintImages.stream()
                .map(this::toDto)
                .toList();
    }

}
