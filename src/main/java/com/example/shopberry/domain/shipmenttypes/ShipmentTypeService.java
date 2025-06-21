package com.example.shopberry.domain.shipmenttypes;

import com.example.shopberry.common.constants.messages.ShipmentTypeMessages;
import com.example.shopberry.domain.shipmenttypes.dto.CreateShipmentTypeRequestDto;
import com.example.shopberry.domain.shipmenttypes.dto.ShipmentTypeDtoMapper;
import com.example.shopberry.domain.shipmenttypes.dto.ShipmentTypeResponseDto;
import com.example.shopberry.domain.shipmenttypes.dto.UpdateShipmentTypeRequestDto;
import jakarta.persistence.EntityExistsException;
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
    public ShipmentTypeResponseDto createShipmentType(CreateShipmentTypeRequestDto createShipmentTypeRequestDto) throws EntityExistsException {
        String shipmentName = createShipmentTypeRequestDto.getShipmentName().trim();

        if (shipmentTypeRepository.existsByShipmentName(shipmentName)) {
            throw new EntityExistsException(ShipmentTypeMessages.SHIPMENT_TYPE_ALREADY_EXISTS);
        }

        ShipmentType shipmentType = new ShipmentType();

        shipmentType.setShipmentName(shipmentName);
        shipmentType.setShipmentCost(createShipmentTypeRequestDto.getShipmentCost());

        return shipmentTypeDtoMapper.toDto(shipmentTypeRepository.save(shipmentType));
    }

    @Transactional
    public ShipmentTypeResponseDto updateShipmentTypeById(Long shipmentTypeId, UpdateShipmentTypeRequestDto updateShipmentTypeRequestDto) throws EntityNotFoundException, EntityExistsException {
        ShipmentType shipmentType = shipmentTypeRepository.findById(shipmentTypeId).orElse(null);

        if (shipmentType == null) {
            throw new EntityNotFoundException(ShipmentTypeMessages.SHIPMENT_TYPE_NOT_FOUND);
        }

        if (updateShipmentTypeRequestDto.getShipmentName() != null) {
            String shipmentName = updateShipmentTypeRequestDto.getShipmentName().trim();

            ShipmentType otherShipmentType = shipmentTypeRepository.findByShipmentName(shipmentName).orElse(null);

            if (otherShipmentType != null && !otherShipmentType.getShipmentTypeId().equals(shipmentTypeId)) {
                throw new EntityExistsException(ShipmentTypeMessages.SHIPMENT_TYPE_ALREADY_EXISTS);
            }

            shipmentType.setShipmentName(shipmentName);
        }

        if (updateShipmentTypeRequestDto.getShipmentCost() != null) {
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
