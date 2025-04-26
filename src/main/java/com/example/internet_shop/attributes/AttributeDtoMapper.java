package com.example.internet_shop.attributes;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AttributeDtoMapper {

    public AttributeDto toDto(Attribute attribute) {
        AttributeDto AttributeDto = new AttributeDto();

        AttributeDto.setAttributeId(attribute.getAttributeId());
        AttributeDto.setAttributeName(attribute.getAttributeName());

        return AttributeDto;
    }

    public List<AttributeDto> toDtoList(List<Attribute> attributes) {
        return attributes.stream()
                .map(this::toDto)
                .toList();
    }

}
