package com.example.shopberry.domain.producers;

import com.example.shopberry.common.constants.messages.ProducerMessages;
import com.example.shopberry.domain.producers.dto.request.CreateProducerRequestDto;
import com.example.shopberry.domain.producers.dto.ProducerDtoMapper;
import com.example.shopberry.domain.producers.dto.response.ProducerResponseDto;
import com.example.shopberry.domain.producers.dto.request.UpdateProducerRequestDto;
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
    public ProducerResponseDto createProducer(CreateProducerRequestDto createProducerRequestDto) throws EntityExistsException {
        String producerName = createProducerRequestDto.getProducerName().trim();

        if (producerRepository.existsByProducerName(producerName)) {
            throw new EntityExistsException(ProducerMessages.PRODUCER_WITH_THAT_NAME_ALREADY_EXISTS);
        }

        Producer producer = new Producer();

        producer.setProducerName(producerName);

        return producerDtoMapper.toDto(producerRepository.save(producer));
    }

    @Transactional
    public ProducerResponseDto updateProducerById(Long producerId, UpdateProducerRequestDto updateProducerRequestDto) throws EntityNotFoundException, EntityExistsException {
        Producer producer = producerRepository.findById(producerId).orElse(null);

        if (producer == null) {
            throw new EntityNotFoundException(ProducerMessages.PRODUCER_NOT_FOUND);
        }

        if (updateProducerRequestDto.getProducerName() != null) {
            String producerName = updateProducerRequestDto.getProducerName().trim();

            Producer otherProducer = producerRepository.findByProducerName(producerName).orElse(null);

            if (otherProducer != null && !producer.getProducerId().equals(otherProducer.getProducerId())) {
                throw new EntityExistsException(ProducerMessages.PRODUCER_WITH_THAT_NAME_ALREADY_EXISTS);
            }

            producer.setProducerName(producerName);
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
