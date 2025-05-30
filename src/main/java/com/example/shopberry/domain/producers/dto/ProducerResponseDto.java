package com.example.shopberry.domain.producers.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProducerResponseDto {

    @JsonProperty("producer_id")
    private Long producerId;

    @JsonProperty("producer_name")
    private String producerName;

}
