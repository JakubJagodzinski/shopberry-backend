package com.example.shopberry.domain.producers;

import com.example.shopberry.auth.access.CheckPermission;
import com.example.shopberry.common.MessageResponseDto;
import com.example.shopberry.domain.producers.dto.request.CreateProducerRequestDto;
import com.example.shopberry.domain.producers.dto.response.ProducerResponseDto;
import com.example.shopberry.domain.producers.dto.request.UpdateProducerRequestDto;
import com.example.shopberry.user.Permission;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
public class ProducerController {

    private final ProducerService producerService;

    @GetMapping("/producers")
    public ResponseEntity<List<ProducerResponseDto>> getAllProducers() {
        List<ProducerResponseDto> producerResponseDtoList = producerService.getAllProducers();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(producerResponseDtoList);
    }

    @GetMapping("/producers/{producerId}")
    public ResponseEntity<ProducerResponseDto> getProducerById(@PathVariable Long producerId) {
        ProducerResponseDto producerResponseDto = producerService.getProducerById(producerId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(producerResponseDto);
    }

    @CheckPermission(Permission.PRODUCER_CREATE)
    @PostMapping("/producers")
    public ResponseEntity<ProducerResponseDto> createProducer(@Valid @RequestBody CreateProducerRequestDto createProducerRequestDto) {
        ProducerResponseDto createdProducerResponseDto = producerService.createProducer(createProducerRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/producers/" + createdProducerResponseDto.getProducerId()))
                .body(createdProducerResponseDto);
    }

    @CheckPermission(Permission.PRODUCER_UPDATE)
    @PatchMapping("/producers/{producerId}")
    public ResponseEntity<ProducerResponseDto> updateProducerById(@Valid @PathVariable Long producerId, @RequestBody UpdateProducerRequestDto updateProducerRequestDto) {
        ProducerResponseDto updatedProducerResponseDto = producerService.updateProducerById(producerId, updateProducerRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedProducerResponseDto);
    }

    @CheckPermission(Permission.PRODUCER_DELETE)
    @DeleteMapping("/producers/{producerId}")
    public ResponseEntity<MessageResponseDto> deleteProducerById(@PathVariable Long producerId) {
        producerService.deleteProducerById(producerId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Producer with id " + producerId + " deleted successfully"));
    }

}
