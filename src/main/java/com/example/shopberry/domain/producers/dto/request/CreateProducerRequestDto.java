package com.example.shopberry.domain.producers.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProducerRequestDto {

    @Schema(
            description = "Unique name of the producer",
            example = "Vortex"
    )
    @NotBlank
    @JsonProperty("producer_name")
    private String producerName;

}
