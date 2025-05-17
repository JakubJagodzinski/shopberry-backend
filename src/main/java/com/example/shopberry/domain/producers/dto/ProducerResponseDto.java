package com.example.shopberry.domain.producers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProducerResponseDto {

    private Long producerId;

    private String producerName;

}
