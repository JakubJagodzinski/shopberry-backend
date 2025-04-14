package com.example.internet_shop.producers;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProducerDtoMapper {

    public ProducerDto toDto(Producer producer) {
        ProducerDto dto = new ProducerDto();

        dto.setProducerId(producer.getProducerId());
        dto.setProducerName(producer.getProducerName());

        return dto;
    }

    public List<ProducerDto> toDtoList(List<Producer> producers) {
        return producers.stream()
                .map(this::toDto)
                .toList();
    }

}
