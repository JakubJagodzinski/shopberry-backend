package com.example.shopberry.domain.producers;

import com.example.shopberry.common.constants.messages.ProducerMessages;
import com.example.shopberry.domain.producers.dto.CreateProducerRequestDto;
import com.example.shopberry.domain.producers.dto.ProducerDtoMapper;
import com.example.shopberry.domain.producers.dto.ProducerResponseDto;
import com.example.shopberry.domain.producers.dto.UpdateProducerRequestDto;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProducerService {

    private final ProducerRepository producerRepository;

    private final ProducerDtoMapper producerDtoMapper;

    public List<ProducerResponseDto> getAllProducers() {
        List<Producer> producers = producerRepository.findAll();
        return producerDtoMapper.toDtoList(producers);
    }

    @Transactional
    public ProducerResponseDto getProducerById(Long producerId) throws EntityNotFoundException {
        Producer producer = producerRepository.findById(producerId).orElse(null);

        if (producer == null) {
            throw new EntityNotFoundException(ProducerMessages.PRODUCER_NOT_FOUND);
        }

        return producerDtoMapper.toDto(producer);
    }

    @Transactional
    public ProducerResponseDto createProducer(CreateProducerRequestDto createProducerRequestDto) throws EntityExistsException, IllegalArgumentException {
        Producer producer = new Producer();

        if (producerRepository.existsByProducerName(createProducerRequestDto.getProducerName())) {
            throw new EntityExistsException(ProducerMessages.PRODUCER_WITH_THAT_NAME_ALREADY_EXISTS);
        }

        if (createProducerRequestDto.getProducerName() == null) {
            throw new IllegalArgumentException(ProducerMessages.PRODUCER_NAME_CANNOT_BE_NULL);
        }

        if (createProducerRequestDto.getProducerName().isEmpty()) {
            throw new IllegalArgumentException(ProducerMessages.PRODUCER_NAME_CANNOT_BE_EMPTY);
        }

        producer.setProducerName(createProducerRequestDto.getProducerName());

        return producerDtoMapper.toDto(producerRepository.save(producer));
    }

    @Transactional
    public ProducerResponseDto updateProducerById(Long producerId, UpdateProducerRequestDto updateProducerRequestDto) throws EntityNotFoundException, EntityExistsException, IllegalArgumentException {
        Producer producer = producerRepository.findById(producerId).orElse(null);

        if (producer == null) {
            throw new EntityNotFoundException(ProducerMessages.PRODUCER_NOT_FOUND);
        }

        if (updateProducerRequestDto.getProducerName() != null) {
            if (updateProducerRequestDto.getProducerName().isEmpty()) {
                throw new IllegalArgumentException(ProducerMessages.PRODUCER_NAME_CANNOT_BE_EMPTY);
            }

            Producer otherProducer = producerRepository.findByProducerName(updateProducerRequestDto.getProducerName()).orElse(null);

            if (otherProducer != null && !producer.getProducerId().equals(otherProducer.getProducerId())) {
                throw new EntityExistsException(ProducerMessages.PRODUCER_WITH_THAT_NAME_ALREADY_EXISTS);
            }

            producer.setProducerName(updateProducerRequestDto.getProducerName());
        }

        return producerDtoMapper.toDto(producerRepository.save(producer));
    }

    @Transactional
    public void deleteProducerById(Long producerId) throws EntityNotFoundException {
        if (!producerRepository.existsById(producerId)) {
            throw new EntityNotFoundException(ProducerMessages.PRODUCER_NOT_FOUND);
        }

        producerRepository.deleteById(producerId);
    }

}
