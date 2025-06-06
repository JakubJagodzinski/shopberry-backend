package com.example.shopberry.domain.producers;

import com.example.shopberry.common.MessageResponseDto;
import com.example.shopberry.domain.producers.dto.CreateProducerRequestDto;
import com.example.shopberry.domain.producers.dto.ProducerResponseDto;
import com.example.shopberry.domain.producers.dto.UpdateProducerRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/producers")
@RequiredArgsConstructor
public class ProducerController {

    private final ProducerService producerService;

    @GetMapping
    public ResponseEntity<List<ProducerResponseDto>> getProducers() {
        List<ProducerResponseDto> producerResponseDtoList = producerService.getProducers();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(producerResponseDtoList);
    }

    @GetMapping("/{producerId}")
    public ResponseEntity<ProducerResponseDto> getProducerById(@PathVariable Long producerId) {
        ProducerResponseDto producerResponseDto = producerService.getProducerById(producerId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(producerResponseDto);
    }

    @PostMapping
    public ResponseEntity<ProducerResponseDto> createProducer(@RequestBody CreateProducerRequestDto createProducerRequestDto) {
        ProducerResponseDto createdProducerResponseDto = producerService.createProducer(createProducerRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/producers/" + createdProducerResponseDto.getProducerId()))
                .body(createdProducerResponseDto);
    }

    @PutMapping("/{producerId}")
    public ResponseEntity<ProducerResponseDto> updateProducerById(@PathVariable Long producerId, @RequestBody UpdateProducerRequestDto updateProducerRequestDto) {
        ProducerResponseDto updatedProducerResponseDto = producerService.updateProducerById(producerId, updateProducerRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedProducerResponseDto);
    }

    @DeleteMapping("/{producerId}")
    public ResponseEntity<MessageResponseDto> deleteProducerById(@PathVariable Long producerId) {
        producerService.deleteProducerById(producerId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Producer with id " + producerId + " deleted successfully"));
    }

}
