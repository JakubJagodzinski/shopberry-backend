package com.example.internet_shop.producers;

import com.example.internet_shop.producers.dto.CreateProducerRequestDto;
import com.example.internet_shop.producers.dto.ProducerResponseDto;
import com.example.internet_shop.producers.dto.UpdateProducerRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/producers")
public class ProducerController {

    private final ProducerService producerService;

    public ProducerController(ProducerService producerService) {
        this.producerService = producerService;
    }

    @GetMapping("/")
    public ResponseEntity<List<ProducerResponseDto>> getProducers() {
        List<ProducerResponseDto> producerResponseDtoList = producerService.getProducers();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(producerResponseDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProducerResponseDto> getProducerById(@PathVariable Long id) {
        ProducerResponseDto producerResponseDto = producerService.getProducerById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(producerResponseDto);
    }

    @PostMapping("/")
    public ResponseEntity<ProducerResponseDto> createProducer(@RequestBody CreateProducerRequestDto createProducerRequestDto) {
        ProducerResponseDto createdProducerResponseDto = producerService.createProducer(createProducerRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/producers/" + createdProducerResponseDto.getProducerId()))
                .body(createdProducerResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProducerResponseDto> updateProducerById(@PathVariable Long id, @RequestBody UpdateProducerRequestDto updateProducerRequestDto) {
        ProducerResponseDto updatedProducerResponseDto = producerService.updateProducerById(id, updateProducerRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedProducerResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProducerById(@PathVariable Long id) {
        producerService.deleteProducerById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Producer with id " + id + " deleted successfully");
    }

}
