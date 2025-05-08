package com.example.internet_shop.shipmenttypes;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipmentTypeService {

    private final ShipmentTypeRepository shipmentTypeRepository;

    private final ShipmentTypeDtoMapper shipmentTypeDtoMapper;

    private final String SHIPMENT_TYPE_NOT_FOUND_MESSAGE = "Shipment type not found";
    private final String SHIPMENT_TYPE_ALREADY_EXISTS_MESSAGE = "Shipment type already exists";
    private final String SHIPMENT_NAME_CANNOT_BE_EMPTY_MESSAGE = "Shipment name cannot be empty";
    private final String SHIPMENT_NAME_CANNOT_BE_NULL_MESSAGE = "Shipment name cannot be null";
    private final String SHIPMENT_COST_CANNOT_BE_NEGATIVE_MESSAGE = "Shipment cost cannot be negative";

    public ShipmentTypeService(ShipmentTypeRepository shipmentTypeRepository, ShipmentTypeDtoMapper shipmentTypeDtoMapper) {
        this.shipmentTypeRepository = shipmentTypeRepository;
        this.shipmentTypeDtoMapper = shipmentTypeDtoMapper;
    }

    public List<ShipmentTypeDto> getShipmentTypes() {
        return shipmentTypeDtoMapper.toDtoList(shipmentTypeRepository.findAll());
    }

    @Transactional
    public ShipmentTypeDto getShipmentTypeById(Long id) throws EntityNotFoundException {
        if (!shipmentTypeRepository.existsById(id)) {
            throw new EntityNotFoundException(SHIPMENT_TYPE_NOT_FOUND_MESSAGE);
        }

        return shipmentTypeDtoMapper.toDto(shipmentTypeRepository.getReferenceById(id));
    }

    @Transactional
    public ShipmentTypeDto createShipmentType(CreateShipmentTypeDto createShipmentTypeDto) throws IllegalArgumentException {
        if (shipmentTypeRepository.existsByShipmentName(createShipmentTypeDto.getShipmentName())) {
            throw new IllegalArgumentException(SHIPMENT_TYPE_ALREADY_EXISTS_MESSAGE);
        }

        if (createShipmentTypeDto.getShipmentName() == null) {
            throw new IllegalArgumentException(SHIPMENT_NAME_CANNOT_BE_NULL_MESSAGE);
        }

        if (createShipmentTypeDto.getShipmentName().isEmpty()) {
            throw new IllegalArgumentException(SHIPMENT_NAME_CANNOT_BE_EMPTY_MESSAGE);
        }

        if (createShipmentTypeDto.getShipmentCost() < 0) {
            throw new IllegalArgumentException(SHIPMENT_COST_CANNOT_BE_NEGATIVE_MESSAGE);
        }

        ShipmentType shipmentType = new ShipmentType();

        shipmentType.setShipmentName(createShipmentTypeDto.getShipmentName());
        shipmentType.setShipmentCost(createShipmentTypeDto.getShipmentCost());

        return shipmentTypeDtoMapper.toDto(shipmentTypeRepository.save(shipmentType));
    }

    @Transactional
    public ShipmentTypeDto updateShipmentTypeById(Long id, UpdateShipmentTypeDto updateShipmentTypeDto) throws EntityNotFoundException, IllegalArgumentException {
        if (!shipmentTypeRepository.existsById(id)) {
            throw new EntityNotFoundException(SHIPMENT_TYPE_NOT_FOUND_MESSAGE);
        }

        ShipmentType shipmentType = shipmentTypeRepository.getReferenceById(id);

        if (updateShipmentTypeDto.getShipmentName() != null) {
            ShipmentType otherShipmentType = shipmentTypeRepository.findByShipmentName(updateShipmentTypeDto.getShipmentName());

            if (otherShipmentType != null && !otherShipmentType.getShipmentTypeId().equals(id)) {
                throw new IllegalArgumentException(SHIPMENT_TYPE_ALREADY_EXISTS_MESSAGE);
            }

            if (updateShipmentTypeDto.getShipmentName().isEmpty()) {
                throw new IllegalArgumentException(SHIPMENT_NAME_CANNOT_BE_EMPTY_MESSAGE);
            }

            shipmentType.setShipmentName(updateShipmentTypeDto.getShipmentName());
        }

        if (updateShipmentTypeDto.getShipmentCost() != null) {
            if (updateShipmentTypeDto.getShipmentCost() < 0) {
                throw new IllegalArgumentException(SHIPMENT_COST_CANNOT_BE_NEGATIVE_MESSAGE);
            }

            shipmentType.setShipmentCost(updateShipmentTypeDto.getShipmentCost());
        }

        return shipmentTypeDtoMapper.toDto(shipmentTypeRepository.save(shipmentType));
    }

    @Transactional
    public void deleteShipmentTypeById(Long id) throws EntityNotFoundException {
        if (!shipmentTypeRepository.existsById(id)) {
            throw new EntityNotFoundException(SHIPMENT_TYPE_NOT_FOUND_MESSAGE);
        }

        shipmentTypeRepository.deleteById(id);
    }

}
