package com.example.shopberry.domain.producers.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"producer_id", "producer_name"})
public class ProducerResponseDto {

    @JsonProperty("producer_id")
    private Long producerId;

    @JsonProperty("producer_name")
    private String producerName;

}
