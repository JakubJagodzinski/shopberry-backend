package com.example.shopberry.domain.producers;

import com.example.shopberry.domain.producers.dto.CreateProducerRequestDto;
import com.example.shopberry.domain.producers.dto.ProducerDtoMapper;
import com.example.shopberry.domain.producers.dto.ProducerResponseDto;
import com.example.shopberry.domain.producers.dto.UpdateProducerRequestDto;
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

    private static final String PRODUCER_ALREADY_EXISTS_MESSAGE = "Producer already exists";
    private static final String PRODUCER_WITH_THAT_NAME_ALREADY_EXISTS = "Producer with that name already exists";
    private static final String PRODUCER_NOT_FOUND_MESSAGE = "Producer not found";

    public List<ProducerResponseDto> getProducers() {
        List<Producer> producers = producerRepository.findAll();
        return producerDtoMapper.toDtoList(producers);
    }

    @Transactional
    public ProducerResponseDto getProducerById(Long id) throws EntityNotFoundException {
        Producer producer = producerRepository.findById(id).orElse(null);

        if (producer == null) {
            throw new EntityNotFoundException(PRODUCER_NOT_FOUND_MESSAGE);
        }

        return producerDtoMapper.toDto(producer);
    }

    @Transactional
    public ProducerResponseDto createProducer(CreateProducerRequestDto createProducerRequestDto) throws IllegalArgumentException {
        Producer producer = new Producer();

        if (producerRepository.existsByProducerName(createProducerRequestDto.getProducerName())) {
            throw new IllegalArgumentException(PRODUCER_ALREADY_EXISTS_MESSAGE);
        }

        producer.setProducerName(createProducerRequestDto.getProducerName());


        return producerDtoMapper.toDto(producerRepository.save(producer));
    }

    @Transactional
    public ProducerResponseDto updateProducerById(Long id, UpdateProducerRequestDto updateProducerRequestDto) throws EntityNotFoundException {
        Producer producer = producerRepository.findById(id).orElse(null);

        if (producer == null) {
            throw new EntityNotFoundException(PRODUCER_NOT_FOUND_MESSAGE);
        }

        if (updateProducerRequestDto.getProducerName() != null) {
            Producer otherProducer = producerRepository.findByProducerName(updateProducerRequestDto.getProducerName());

            if (otherProducer != null && !producer.getProducerId().equals(otherProducer.getProducerId())) {
                throw new IllegalArgumentException(PRODUCER_WITH_THAT_NAME_ALREADY_EXISTS);
            }

            producer.setProducerName(updateProducerRequestDto.getProducerName());
        }

        return producerDtoMapper.toDto(producerRepository.save(producer));
    }

    @Transactional
    public void deleteProducerById(Long id) throws EntityNotFoundException {
        if (!producerRepository.existsById(id)) {
            throw new EntityNotFoundException(PRODUCER_NOT_FOUND_MESSAGE);
        }

        producerRepository.deleteById(id);
    }

}
