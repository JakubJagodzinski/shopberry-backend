package com.example.internet_shop.producers;

import com.example.internet_shop.producers.dto.CreateProducerRequestDto;
import com.example.internet_shop.producers.dto.ProducerResponseDto;
import com.example.internet_shop.producers.dto.ProducerDtoMapper;
import com.example.internet_shop.producers.dto.UpdateProducerRequestDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProducerService {

    private final ProducerRepository producerRepository;

    private final ProducerDtoMapper producerDtoMapper;

    private final String PRODUCER_ALREADY_EXISTS_MESSAGE = "Producer already exists";
    private final String PRODUCER_WITH_THAT_NAME_ALREADY_EXISTS = "Producer with that name already exists";
    private final String PRODUCER_NOT_FOUND_MESSAGE = "Producer not found";

    @Autowired
    public ProducerService(ProducerRepository producerRepository, ProducerDtoMapper producerDtoMapper) {
        this.producerRepository = producerRepository;
        this.producerDtoMapper = producerDtoMapper;
    }

    public List<ProducerResponseDto> getProducers() {
        List<Producer> producers = producerRepository.findAll();
        return producerDtoMapper.toDtoList(producers);
    }

    @Transactional
    public ProducerResponseDto getProducerById(Long id) throws EntityNotFoundException {
        if (!producerRepository.existsById(id)) {
            throw new EntityNotFoundException(PRODUCER_NOT_FOUND_MESSAGE);
        }

        return producerDtoMapper.toDto(producerRepository.getReferenceById(id));
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
        if (!producerRepository.existsById(id)) {
            throw new EntityNotFoundException(PRODUCER_NOT_FOUND_MESSAGE);
        }

        Producer producer = producerRepository.getReferenceById(id);

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
