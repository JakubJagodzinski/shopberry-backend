package com.example.internet_shop.producers;

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

    public List<ProducerDto> getProducers() {
        List<Producer> producers = producerRepository.findAll();
        return producerDtoMapper.toDtoList(producers);
    }

    @Transactional
    public ProducerDto getProducerById(Long id) throws EntityNotFoundException {
        if (!producerRepository.existsById(id)) {
            throw new EntityNotFoundException(PRODUCER_NOT_FOUND_MESSAGE);
        }

        return producerDtoMapper.toDto(producerRepository.getReferenceById(id));
    }

    @Transactional
    public ProducerDto createProducer(CreateProducerDto createProducerDto) throws IllegalStateException {
        Producer producer = new Producer();

        if (producerRepository.existsByProducerName(createProducerDto.getProducerName())) {
            throw new IllegalStateException(PRODUCER_ALREADY_EXISTS_MESSAGE);
        }

        producer.setProducerName(createProducerDto.getProducerName());


        return producerDtoMapper.toDto(producerRepository.save(producer));
    }

    @Transactional
    public ProducerDto editProducerById(Long id, CreateProducerDto createProducerDto) throws EntityNotFoundException {
        if (!producerRepository.existsById(id)) {
            throw new EntityNotFoundException(PRODUCER_NOT_FOUND_MESSAGE);
        }

        Producer producer = producerRepository.getReferenceById(id);

        if (createProducerDto.getProducerName() != null) {
            Producer otherProducer = producerRepository.findByProducerName(createProducerDto.getProducerName());

            if (otherProducer != null && !producer.getProducerId().equals(otherProducer.getProducerId())) {
                throw new IllegalStateException(PRODUCER_WITH_THAT_NAME_ALREADY_EXISTS);
            }

            producer.setProducerName(createProducerDto.getProducerName());
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
