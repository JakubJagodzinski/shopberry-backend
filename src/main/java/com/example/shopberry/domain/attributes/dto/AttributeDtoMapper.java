package com.example.shopberry.domain.attributes.dto;

import com.example.shopberry.domain.attributes.Attribute;
import com.example.shopberry.domain.attributes.dto.response.AttributeResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AttributeDtoMapper {

    public AttributeResponseDto toDto(Attribute attribute) {
        if (attribute == null) {
            return null;
        }

        AttributeResponseDto AttributeResponseDto = new AttributeResponseDto();

        AttributeResponseDto.setAttributeId(attribute.getAttributeId());
        AttributeResponseDto.setAttributeName(attribute.getAttributeName());

        return AttributeResponseDto;
    }

    public List<AttributeResponseDto> toDtoList(List<Attribute> attributeList) {
        return attributeList.stream()
                .map(this::toDto)
                .toList();
    }

}
