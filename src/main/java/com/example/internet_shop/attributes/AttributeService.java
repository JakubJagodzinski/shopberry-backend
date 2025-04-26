package com.example.internet_shop.attributes;

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

    public List<AttributeDto> getAttributes() {
        return attributeDtoMapper.toDtoList(attributeRepository.findAll());
    }

    @Transactional
    public AttributeDto getAttributeById(Long id) throws EntityNotFoundException {
        if (!attributeRepository.existsById(id)) {
            throw new EntityNotFoundException(ATTRIBUTE_NOT_FOUND_MESSAGE);
        }

        return attributeDtoMapper.toDto(attributeRepository.getReferenceById(id));
    }

    @Transactional
    public AttributeDto createAttribute(CreateAttributeDto createAttributeDto) throws IllegalStateException {
        Attribute attribute = new Attribute();

        if (attributeRepository.existsByAttributeName(createAttributeDto.getAttributeName())) {
            throw new IllegalStateException(ATTRIBUTE_WITH_THAT_NAME_ALREADY_EXISTS_MESSAGE);
        }

        if (createAttributeDto.getAttributeName() == null) {
            throw new IllegalStateException(ATTRIBUTE_NAME_CANNOT_BY_NULL_MESSAGE);
        }

        if (createAttributeDto.getAttributeName().isEmpty()) {
            throw new IllegalStateException(ATTRIBUTE_NAME_CANNOT_BY_EMPTY_MESSAGE);
        }

        attribute.setAttributeName(createAttributeDto.getAttributeName());

        return attributeDtoMapper.toDto(attributeRepository.save(attribute));
    }

    @Transactional
    public AttributeDto updateAttributeById(Long id, UpdateAttributeDto updateAttributeDto) throws EntityNotFoundException, IllegalStateException {
        if (!attributeRepository.existsById(id)) {
            throw new EntityNotFoundException(ATTRIBUTE_NOT_FOUND_MESSAGE);
        }

        Attribute attribute = attributeRepository.getReferenceById(id);

        if (updateAttributeDto.getAttributeName() != null) {
            Attribute otherAttribute = attributeRepository.findByAttributeName(updateAttributeDto.getAttributeName());

            if (otherAttribute != null && !otherAttribute.getAttributeId().equals(attribute.getAttributeId())) {
                throw new IllegalStateException(ATTRIBUTE_WITH_THAT_NAME_ALREADY_EXISTS_MESSAGE);
            }

            if (updateAttributeDto.getAttributeName().isEmpty()) {
                throw new IllegalStateException(ATTRIBUTE_NAME_CANNOT_BY_EMPTY_MESSAGE);
            }

            attribute.setAttributeName(updateAttributeDto.getAttributeName());
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
