package com.example.shopberry.domain.shipmenttypes;

import com.example.shopberry.common.constants.messages.ShipmentTypeMessages;
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

    public List<ShipmentTypeResponseDto> getAllShipmentTypes() {
        return shipmentTypeDtoMapper.toDtoList(shipmentTypeRepository.findAll());
    }

    @Transactional
    public ShipmentTypeResponseDto getShipmentTypeById(Long shipmentTypeId) throws EntityNotFoundException {
        ShipmentType shipmentType = shipmentTypeRepository.findById(shipmentTypeId).orElse(null);

        if (shipmentType == null) {
            throw new EntityNotFoundException(ShipmentTypeMessages.SHIPMENT_TYPE_NOT_FOUND);
        }

        return shipmentTypeDtoMapper.toDto(shipmentType);
    }

    @Transactional
    public ShipmentTypeResponseDto createShipmentType(CreateShipmentTypeRequestDto createShipmentTypeRequestDto) throws IllegalArgumentException {
        if (shipmentTypeRepository.existsByShipmentName(createShipmentTypeRequestDto.getShipmentName())) {
            throw new IllegalArgumentException(ShipmentTypeMessages.SHIPMENT_TYPE_ALREADY_EXISTS);
        }

        if (createShipmentTypeRequestDto.getShipmentName() == null) {
            throw new IllegalArgumentException(ShipmentTypeMessages.SHIPMENT_NAME_CANNOT_BE_NULL);
        }

        if (createShipmentTypeRequestDto.getShipmentName().isEmpty()) {
            throw new IllegalArgumentException(ShipmentTypeMessages.SHIPMENT_NAME_CANNOT_BE_EMPTY);
        }

        if (createShipmentTypeRequestDto.getShipmentCost() < 0) {
            throw new IllegalArgumentException(ShipmentTypeMessages.SHIPMENT_COST_CANNOT_BE_NEGATIVE);
        }

        ShipmentType shipmentType = new ShipmentType();

        shipmentType.setShipmentName(createShipmentTypeRequestDto.getShipmentName());
        shipmentType.setShipmentCost(createShipmentTypeRequestDto.getShipmentCost());

        return shipmentTypeDtoMapper.toDto(shipmentTypeRepository.save(shipmentType));
    }

    @Transactional
    public ShipmentTypeResponseDto updateShipmentTypeById(Long shipmentTypeId, UpdateShipmentTypeRequestDto updateShipmentTypeRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        ShipmentType shipmentType = shipmentTypeRepository.findById(shipmentTypeId).orElse(null);

        if (shipmentType == null) {
            throw new EntityNotFoundException(ShipmentTypeMessages.SHIPMENT_TYPE_NOT_FOUND);
        }

        if (updateShipmentTypeRequestDto.getShipmentName() != null) {
            ShipmentType otherShipmentType = shipmentTypeRepository.findByShipmentName(updateShipmentTypeRequestDto.getShipmentName()).orElse(null);

            if (otherShipmentType != null && !otherShipmentType.getShipmentTypeId().equals(shipmentTypeId)) {
                throw new IllegalArgumentException(ShipmentTypeMessages.SHIPMENT_TYPE_ALREADY_EXISTS);
            }

            if (updateShipmentTypeRequestDto.getShipmentName().isEmpty()) {
                throw new IllegalArgumentException(ShipmentTypeMessages.SHIPMENT_NAME_CANNOT_BE_EMPTY);
            }

            shipmentType.setShipmentName(updateShipmentTypeRequestDto.getShipmentName());
        }

        if (updateShipmentTypeRequestDto.getShipmentCost() != null) {
            if (updateShipmentTypeRequestDto.getShipmentCost() < 0) {
                throw new IllegalArgumentException(ShipmentTypeMessages.SHIPMENT_COST_CANNOT_BE_NEGATIVE);
            }

            shipmentType.setShipmentCost(updateShipmentTypeRequestDto.getShipmentCost());
        }

        return shipmentTypeDtoMapper.toDto(shipmentTypeRepository.save(shipmentType));
    }

    @Transactional
    public void deleteShipmentTypeById(Long shipmentTypeId) throws EntityNotFoundException {
        if (!shipmentTypeRepository.existsById(shipmentTypeId)) {
            throw new EntityNotFoundException(ShipmentTypeMessages.SHIPMENT_TYPE_NOT_FOUND);
        }

        shipmentTypeRepository.deleteById(shipmentTypeId);
    }

}
