package com.example.shopberry.domain.attributes;

import com.example.shopberry.common.constants.messages.AttributeMessages;
import com.example.shopberry.domain.attributes.dto.AttributeDtoMapper;
import com.example.shopberry.domain.attributes.dto.AttributeResponseDto;
import com.example.shopberry.domain.attributes.dto.CreateAttributeRequestDto;
import com.example.shopberry.domain.attributes.dto.UpdateAttributeRequestDto;
import jakarta.persistence.EntityExistsException;
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

    public List<AttributeResponseDto> getAllAttributes() {
        return attributeDtoMapper.toDtoList(attributeRepository.findAll());
    }

    @Transactional
    public AttributeResponseDto getAttributeById(Long attributeId) throws EntityNotFoundException {
        Attribute attribute = attributeRepository.findById(attributeId).orElse(null);

        if (attribute == null) {
            throw new EntityNotFoundException(AttributeMessages.ATTRIBUTE_NOT_FOUND);
        }

        return attributeDtoMapper.toDto(attribute);
    }

    @Transactional
    public AttributeResponseDto createAttribute(CreateAttributeRequestDto createAttributeRequestDto) throws EntityExistsException, IllegalArgumentException {
        Attribute attribute = new Attribute();

        if (attributeRepository.existsByAttributeName(createAttributeRequestDto.getAttributeName())) {
            throw new EntityExistsException(AttributeMessages.ATTRIBUTE_WITH_THAT_NAME_ALREADY_EXISTS);
        }

        if (createAttributeRequestDto.getAttributeName() == null) {
            throw new IllegalArgumentException(AttributeMessages.ATTRIBUTE_NAME_CANNOT_BE_NULL);
        }

        if (createAttributeRequestDto.getAttributeName().isEmpty()) {
            throw new IllegalArgumentException(AttributeMessages.ATTRIBUTE_NAME_CANNOT_BE_EMPTY);
        }

        attribute.setAttributeName(createAttributeRequestDto.getAttributeName());

        return attributeDtoMapper.toDto(attributeRepository.save(attribute));
    }

    @Transactional
    public AttributeResponseDto updateAttributeById(Long attributeId, UpdateAttributeRequestDto updateAttributeRequestDto) throws EntityNotFoundException, EntityExistsException, IllegalArgumentException {
        Attribute attribute = attributeRepository.findById(attributeId).orElse(null);

        if (attribute == null) {
            throw new EntityNotFoundException(AttributeMessages.ATTRIBUTE_NOT_FOUND);
        }

        if (updateAttributeRequestDto.getAttributeName() != null) {
            Attribute otherAttribute = attributeRepository.findByAttributeName(updateAttributeRequestDto.getAttributeName()).orElse(null);

            if (otherAttribute != null && !otherAttribute.getAttributeId().equals(attribute.getAttributeId())) {
                throw new EntityExistsException(AttributeMessages.ATTRIBUTE_WITH_THAT_NAME_ALREADY_EXISTS);
            }

            if (updateAttributeRequestDto.getAttributeName().isEmpty()) {
                throw new IllegalArgumentException(AttributeMessages.ATTRIBUTE_NAME_CANNOT_BE_EMPTY);
            }

            attribute.setAttributeName(updateAttributeRequestDto.getAttributeName());
        }

        return attributeDtoMapper.toDto(attributeRepository.save(attribute));
    }

    @Transactional
    public void deleteAttributeById(Long attributeId) throws EntityNotFoundException {
        if (!attributeRepository.existsById(attributeId)) {
            throw new EntityNotFoundException(AttributeMessages.ATTRIBUTE_NOT_FOUND);
        }

        attributeRepository.deleteById(attributeId);
    }

}
