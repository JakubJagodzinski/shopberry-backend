package com.example.internet_shop.complaintimages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateComplaintImageDto {

    private Long id;

    private Long complaintId;

    private byte[] image;

}
