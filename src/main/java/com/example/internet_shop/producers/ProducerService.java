package com.example.internet_shop.producers;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProducerService {

    private final ProducerRepository producerRepository;

    private final ProducerDtoMapper producerDtoMapper;

    private final String PRODUCER_ALREADY_EXISTS_MESSAGE = "Producer already exists";

    @Autowired
    public ProducerService(ProducerRepository producerRepository, ProducerDtoMapper producerDtoMapper) {
        this.producerRepository = producerRepository;
        this.producerDtoMapper = producerDtoMapper;
    }

    public List<ProducerDto> getProducers() {
        List<Producer> producers = producerRepository.findAll();
        return producerDtoMapper.toDtoList(producers);
    }

    public ProducerDto createProducer(CreateProducerDto createProducerDto) throws IllegalStateException {
        Producer producer = new Producer();

        if (producerRepository.existsByProducerName(createProducerDto.getProducerName())) {
            throw new IllegalStateException(PRODUCER_ALREADY_EXISTS_MESSAGE);
        }

        producer.setProducerName(createProducerDto.getProducerName());


        return producerDtoMapper.toDto(producerRepository.save(producer));
    }

}
