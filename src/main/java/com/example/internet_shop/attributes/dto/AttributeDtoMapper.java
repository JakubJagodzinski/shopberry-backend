package com.example.internet_shop.attributes.dto;

import com.example.internet_shop.attributes.Attribute;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AttributeDtoMapper {

    public AttributeResponseDto toDto(Attribute attribute) {
        AttributeResponseDto AttributeResponseDto = new AttributeResponseDto();

        AttributeResponseDto.setAttributeId(attribute.getAttributeId());
        AttributeResponseDto.setAttributeName(attribute.getAttributeName());

        return AttributeResponseDto;
    }

    public List<AttributeResponseDto> toDtoList(List<Attribute> attributes) {
        return attributes.stream()
                .map(this::toDto)
                .toList();
    }

}
