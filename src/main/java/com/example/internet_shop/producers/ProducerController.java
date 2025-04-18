package com.example.internet_shop.producers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/producers")
public class ProducerController {

    private final ProducerService producerService;

    public ProducerController(ProducerService producerService) {
        this.producerService = producerService;
    }

    @GetMapping("/")
    public ResponseEntity<List<ProducerDto>> getProducers() {
        return ResponseEntity.ok(producerService.getProducers());
    }

    @PostMapping("/")
    public ResponseEntity<ProducerDto> createProducer(@RequestBody CreateProducerDto createProducerDto) {
        ProducerDto createdProducer = producerService.createProducer(createProducerDto);
        return ResponseEntity.created(URI.create("/api/producers/" + createdProducer.getProducerId())).body(createdProducer);
    }

}
