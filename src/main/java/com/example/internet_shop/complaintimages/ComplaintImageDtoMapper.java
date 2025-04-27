package com.example.internet_shop.complaintimages;


import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ComplaintImageDtoMapper {

    public ComplaintImageDto toDto(ComplaintImage ComplaintImage) {
        ComplaintImageDto ComplaintImageDto = new ComplaintImageDto();

        ComplaintImageDto.setId(ComplaintImage.getId());
        ComplaintImageDto.setImage(ComplaintImage.getImage());

        return ComplaintImageDto;
    }

    public List<ComplaintImageDto> toDtoList(List<ComplaintImage> ComplaintImages) {
        return ComplaintImages.stream()
                .map(this::toDto)
                .toList();
    }

}
