package com.example.shopberry.domain.shipmenttypes;

import com.example.shopberry.domain.shipmenttypes.dto.CreateShipmentTypeRequestDto;
import com.example.shopberry.domain.shipmenttypes.dto.ShipmentTypeDtoMapper;
import com.example.shopberry.domain.shipmenttypes.dto.ShipmentTypeResponseDto;
import com.example.shopberry.domain.shipmenttypes.dto.UpdateShipmentTypeRequestDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShipmentTypeService {

    private final ShipmentTypeRepository shipmentTypeRepository;

    private final ShipmentTypeDtoMapper shipmentTypeDtoMapper;

    private static final String SHIPMENT_TYPE_NOT_FOUND_MESSAGE = "Shipment type not found";
    private static final String SHIPMENT_TYPE_ALREADY_EXISTS_MESSAGE = "Shipment type already exists";
    private static final String SHIPMENT_NAME_CANNOT_BE_EMPTY_MESSAGE = "Shipment name cannot be empty";
    private static final String SHIPMENT_NAME_CANNOT_BE_NULL_MESSAGE = "Shipment name cannot be null";
    private static final String SHIPMENT_COST_CANNOT_BE_NEGATIVE_MESSAGE = "Shipment cost cannot be negative";

    public List<ShipmentTypeResponseDto> getAllShipmentTypes() {
        return shipmentTypeDtoMapper.toDtoList(shipmentTypeRepository.findAll());
    }

    @Transactional
    public ShipmentTypeResponseDto getShipmentTypeById(Long id) throws EntityNotFoundException {
        ShipmentType shipmentType = shipmentTypeRepository.findById(id).orElse(null);

        if (shipmentType == null) {
            throw new EntityNotFoundException(SHIPMENT_TYPE_NOT_FOUND_MESSAGE);
        }

        return shipmentTypeDtoMapper.toDto(shipmentType);
    }

    @Transactional
    public ShipmentTypeResponseDto createShipmentType(CreateShipmentTypeRequestDto createShipmentTypeRequestDto) throws IllegalArgumentException {
        if (shipmentTypeRepository.existsByShipmentName(createShipmentTypeRequestDto.getShipmentName())) {
            throw new IllegalArgumentException(SHIPMENT_TYPE_ALREADY_EXISTS_MESSAGE);
        }

        if (createShipmentTypeRequestDto.getShipmentName() == null) {
            throw new IllegalArgumentException(SHIPMENT_NAME_CANNOT_BE_NULL_MESSAGE);
        }

        if (createShipmentTypeRequestDto.getShipmentName().isEmpty()) {
            throw new IllegalArgumentException(SHIPMENT_NAME_CANNOT_BE_EMPTY_MESSAGE);
        }

        if (createShipmentTypeRequestDto.getShipmentCost() < 0) {
            throw new IllegalArgumentException(SHIPMENT_COST_CANNOT_BE_NEGATIVE_MESSAGE);
        }

        ShipmentType shipmentType = new ShipmentType();

        shipmentType.setShipmentName(createShipmentTypeRequestDto.getShipmentName());
        shipmentType.setShipmentCost(createShipmentTypeRequestDto.getShipmentCost());

        return shipmentTypeDtoMapper.toDto(shipmentTypeRepository.save(shipmentType));
    }

    @Transactional
    public ShipmentTypeResponseDto updateShipmentTypeById(Long id, UpdateShipmentTypeRequestDto updateShipmentTypeRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        ShipmentType shipmentType = shipmentTypeRepository.findById(id).orElse(null);

        if (shipmentType == null) {
            throw new EntityNotFoundException(SHIPMENT_TYPE_NOT_FOUND_MESSAGE);
        }

        if (updateShipmentTypeRequestDto.getShipmentName() != null) {
            ShipmentType otherShipmentType = shipmentTypeRepository.findByShipmentName(updateShipmentTypeRequestDto.getShipmentName()).orElse(null);

            if (otherShipmentType != null && !otherShipmentType.getShipmentTypeId().equals(id)) {
                throw new IllegalArgumentException(SHIPMENT_TYPE_ALREADY_EXISTS_MESSAGE);
            }

            if (updateShipmentTypeRequestDto.getShipmentName().isEmpty()) {
                throw new IllegalArgumentException(SHIPMENT_NAME_CANNOT_BE_EMPTY_MESSAGE);
            }

            shipmentType.setShipmentName(updateShipmentTypeRequestDto.getShipmentName());
        }

        if (updateShipmentTypeRequestDto.getShipmentCost() != null) {
            if (updateShipmentTypeRequestDto.getShipmentCost() < 0) {
                throw new IllegalArgumentException(SHIPMENT_COST_CANNOT_BE_NEGATIVE_MESSAGE);
            }

            shipmentType.setShipmentCost(updateShipmentTypeRequestDto.getShipmentCost());
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
