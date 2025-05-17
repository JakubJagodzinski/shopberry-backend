package com.example.shopberry.domain.complaintimages.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComplaintImageResponseDto {

    private Long id;

    private byte[] image;

}
