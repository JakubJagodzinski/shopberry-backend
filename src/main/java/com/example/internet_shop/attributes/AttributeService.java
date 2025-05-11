package com.example.internet_shop.attributes;

import com.example.internet_shop.attributes.dto.AttributeResponseDto;
import com.example.internet_shop.attributes.dto.AttributeDtoMapper;
import com.example.internet_shop.attributes.dto.CreateAttributeRequestDto;
import com.example.internet_shop.attributes.dto.UpdateAttributeRequestDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttributeService {

    private final AttributeRepository attributeRepository;

    private final AttributeDtoMapper attributeDtoMapper;

    private final String ATTRIBUTE_NOT_FOUND_MESSAGE = "Attribute not found";
    private final String ATTRIBUTE_WITH_THAT_NAME_ALREADY_EXISTS_MESSAGE = "Attribute with that name already exists";
    private final String ATTRIBUTE_NAME_CANNOT_BY_NULL_MESSAGE = "Attribute name cannot be null";
    private final String ATTRIBUTE_NAME_CANNOT_BY_EMPTY_MESSAGE = "Attribute name cannot be empty";

    @Autowired
    public AttributeService(AttributeRepository attributeRepository, AttributeDtoMapper attributeDtoMapper) {
        this.attributeRepository = attributeRepository;
        this.attributeDtoMapper = attributeDtoMapper;
    }

    public List<AttributeResponseDto> getAttributes() {
        return attributeDtoMapper.toDtoList(attributeRepository.findAll());
    }

    @Transactional
    public AttributeResponseDto getAttributeById(Long id) throws EntityNotFoundException {
        if (!attributeRepository.existsById(id)) {
            throw new EntityNotFoundException(ATTRIBUTE_NOT_FOUND_MESSAGE);
        }

        return attributeDtoMapper.toDto(attributeRepository.getReferenceById(id));
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
        if (!attributeRepository.existsById(id)) {
            throw new EntityNotFoundException(ATTRIBUTE_NOT_FOUND_MESSAGE);
        }

        Attribute attribute = attributeRepository.getReferenceById(id);

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
