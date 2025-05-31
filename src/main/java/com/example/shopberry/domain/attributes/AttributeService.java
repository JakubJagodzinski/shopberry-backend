package com.example.shopberry.domain.attributes;

import com.example.shopberry.domain.attributes.dto.AttributeDtoMapper;
import com.example.shopberry.domain.attributes.dto.AttributeResponseDto;
import com.example.shopberry.domain.attributes.dto.CreateAttributeRequestDto;
import com.example.shopberry.domain.attributes.dto.UpdateAttributeRequestDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttributeService {

    private final AttributeRepository attributeRepository;

    private final AttributeDtoMapper attributeDtoMapper;

    private final String ATTRIBUTE_NOT_FOUND_MESSAGE = "Attribute not found";
    private final String ATTRIBUTE_WITH_THAT_NAME_ALREADY_EXISTS_MESSAGE = "Attribute with that name already exists";
    private final String ATTRIBUTE_NAME_CANNOT_BY_NULL_MESSAGE = "Attribute name cannot be null";
    private final String ATTRIBUTE_NAME_CANNOT_BY_EMPTY_MESSAGE = "Attribute name cannot be empty";

    public List<AttributeResponseDto> getAttributes() {
        return attributeDtoMapper.toDtoList(attributeRepository.findAll());
    }

    @Transactional
    public AttributeResponseDto getAttributeById(Long id) throws EntityNotFoundException {
        Attribute attribute = attributeRepository.findById(id).orElse(null);

        if (attribute == null) {
            throw new EntityNotFoundException(ATTRIBUTE_NOT_FOUND_MESSAGE);
        }

        return attributeDtoMapper.toDto(attribute);
    }

    @Transactional
    public AttributeResponseDto createAttribute(CreateAttributeRequestDto createAttributeRequestDto) throws IllegalArgumentException {
        Attribute attribute = new Attribute();

        if (attributeRepository.existsByAttributeName(createAttributeRequestDto.getAttributeName())) {
            throw new IllegalArgumentException(ATTRIBUTE_WITH_THAT_NAME_ALREADY_EXISTS_MESSAGE);
        }

        if (createAttributeRequestDto.getAttributeName() == null) {
            throw new IllegalArgumentException(ATTRIBUTE_NAME_CANNOT_BY_NULL_MESSAGE);
        }

        if (createAttributeRequestDto.getAttributeName().isEmpty()) {
            throw new IllegalArgumentException(ATTRIBUTE_NAME_CANNOT_BY_EMPTY_MESSAGE);
        }

        attribute.setAttributeName(createAttributeRequestDto.getAttributeName());

        return attributeDtoMapper.toDto(attributeRepository.save(attribute));
    }

    @Transactional
    public AttributeResponseDto updateAttributeById(Long id, UpdateAttributeRequestDto updateAttributeRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        Attribute attribute = attributeRepository.findById(id).orElse(null);

        if (attribute == null) {
            throw new EntityNotFoundException(ATTRIBUTE_NOT_FOUND_MESSAGE);
        }

        if (updateAttributeRequestDto.getAttributeName() != null) {
            Attribute otherAttribute = attributeRepository.findByAttributeName(updateAttributeRequestDto.getAttributeName());

            if (otherAttribute != null && !otherAttribute.getAttributeId().equals(attribute.getAttributeId())) {
                throw new IllegalArgumentException(ATTRIBUTE_WITH_THAT_NAME_ALREADY_EXISTS_MESSAGE);
            }

            if (updateAttributeRequestDto.getAttributeName().isEmpty()) {
                throw new IllegalArgumentException(ATTRIBUTE_NAME_CANNOT_BY_EMPTY_MESSAGE);
            }

            attribute.setAttributeName(updateAttributeRequestDto.getAttributeName());
        }

        return attributeDtoMapper.toDto(attributeRepository.save(attribute));
    }

    @Transactional
    public void deleteAttributeById(Long id) throws EntityNotFoundException {
        if (!attributeRepository.existsById(id)) {
            throw new EntityNotFoundException(ATTRIBUTE_NOT_FOUND_MESSAGE);
        }

        attributeRepository.deleteById(id);
    }

}
