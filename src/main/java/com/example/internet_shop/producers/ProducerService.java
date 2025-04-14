package com.example.internet_shop.producers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProducerService {

    private final ProducerRepository producerRepository;

    private final ProducerDtoMapper producerDtoMapper;

    @Autowired
    public ProducerService(ProducerRepository producerRepository, ProducerDtoMapper producerDtoMapper) {
        this.producerRepository = producerRepository;
        this.producerDtoMapper = producerDtoMapper;
    }

    public List<ProducerDto> getProducers() {
        List<Producer> producers = producerRepository.findAll();
        return producerDtoMapper.toDtoList(producers);
    }

}
