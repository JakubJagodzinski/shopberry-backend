package com.example.shopberry.domain.producers.dto.request;

import com.example.shopberry.common.validation.NotEmptyIfPresent;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProducerRequestDto {

    @Schema(
            description = "Unique name of the producer",
            example = "Vortex",
            minLength = 1,
            nullable = true
    )
    @NotEmptyIfPresent
    @JsonProperty("producer_name")
    private String producerName;

}
