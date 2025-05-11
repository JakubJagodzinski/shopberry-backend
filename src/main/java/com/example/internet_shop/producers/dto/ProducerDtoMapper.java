package com.example.internet_shop.producers.dto;

import com.example.internet_shop.producers.Producer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProducerDtoMapper {

    public ProducerResponseDto toDto(Producer producer) {
        ProducerResponseDto dto = new ProducerResponseDto();

        dto.setProducerId(producer.getProducerId());
        dto.setProducerName(producer.getProducerName());

        return dto;
    }

    public List<ProducerResponseDto> toDtoList(List<Producer> producers) {
        return producers.stream()
                .map(this::toDto)
                .toList();
    }

}
