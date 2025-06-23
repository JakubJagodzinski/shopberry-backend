package com.example.shopberry.domain.producers.dto;

import com.example.shopberry.domain.producers.Producer;
import com.example.shopberry.domain.producers.dto.response.ProducerResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProducerDtoMapper {

    public ProducerResponseDto toDto(Producer producer) {
        if (producer == null) {
            return null;
        }

        ProducerResponseDto dto = new ProducerResponseDto();

        dto.setProducerId(producer.getProducerId());
        dto.setProducerName(producer.getProducerName());

        return dto;
    }

    public List<ProducerResponseDto> toDtoList(List<Producer> producerList) {
        return producerList.stream()
                .map(this::toDto)
                .toList();
    }

}
