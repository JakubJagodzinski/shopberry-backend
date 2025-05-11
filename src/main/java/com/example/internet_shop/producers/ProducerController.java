package com.example.internet_shop.producers;

import com.example.internet_shop.producers.dto.CreateProducerRequestDto;
import com.example.internet_shop.producers.dto.ProducerResponseDto;
import com.example.internet_shop.producers.dto.UpdateProducerRequestDto;
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
    public ResponseEntity<List<ProducerResponseDto>> getProducers() {
        return ResponseEntity.ok(producerService.getProducers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProducerResponseDto> getProducerById(@PathVariable Long id) {
        return ResponseEntity.ok(producerService.getProducerById(id));
    }

    @PostMapping("/")
    public ResponseEntity<ProducerResponseDto> createProducer(@RequestBody CreateProducerRequestDto createProducerRequestDto) {
        ProducerResponseDto createdProducer = producerService.createProducer(createProducerRequestDto);
        return ResponseEntity.created(URI.create("/api/producers/" + createdProducer.getProducerId())).body(createdProducer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProducerResponseDto> updateProducerById(@PathVariable Long id, @RequestBody UpdateProducerRequestDto updateProducerRequestDto) {
        ProducerResponseDto updatedProducer = producerService.updateProducerById(id, updateProducerRequestDto);
        return ResponseEntity.ok(updatedProducer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProducerById(@PathVariable Long id) {
        producerService.deleteProducerById(id);
        return ResponseEntity.ok("Producer with ID " + id + " deleted successfully.");
    }

}
