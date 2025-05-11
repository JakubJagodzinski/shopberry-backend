package com.example.internet_shop.causesofreturn;

import com.example.internet_shop.causesofreturn.dto.CauseOfReturnResponseDto;
import com.example.internet_shop.causesofreturn.dto.CreateCauseOfReturnRequestDto;
import com.example.internet_shop.causesofreturn.dto.UpdateCauseOfReturnRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/causes-of-return")
public class CauseOfReturnController {

    private final CauseOfReturnService causeOfReturnService;

    public CauseOfReturnController(CauseOfReturnService causeOfReturnService) {
        this.causeOfReturnService = causeOfReturnService;
    }

    @GetMapping("/")
    public ResponseEntity<List<CauseOfReturnResponseDto>> getCausesOfReturn() {
        List<CauseOfReturnResponseDto> causeOfReturnResponseDtoList = causeOfReturnService.getCausesOfReturn();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(causeOfReturnResponseDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CauseOfReturnResponseDto> getCauseOfReturnById(@PathVariable Long id) {
        CauseOfReturnResponseDto causeOfReturnResponseDto = causeOfReturnService.getCauseOfReturnById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(causeOfReturnResponseDto);
    }

    @PostMapping("/")
    public ResponseEntity<CauseOfReturnResponseDto> createCauseOfReturn(@RequestBody CreateCauseOfReturnRequestDto createCauseOfReturnRequestDto) {
        CauseOfReturnResponseDto createdCauseOfReturnResponseDto = causeOfReturnService.createCauseOfReturn(createCauseOfReturnRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/causes-of-return/" + createdCauseOfReturnResponseDto.getCauseOfReturnId()))
                .body(createdCauseOfReturnResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CauseOfReturnResponseDto> updateCauseOfReturnById(@PathVariable Long id, @RequestBody UpdateCauseOfReturnRequestDto updateCauseOfReturnRequestDto) {
        CauseOfReturnResponseDto updatedCauseOfReturnResponseDto = causeOfReturnService.updateCauseOfReturnById(id, updateCauseOfReturnRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedCauseOfReturnResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCauseOfReturnById(@PathVariable Long id) {
        causeOfReturnService.deleteCauseOfReturnById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Cause of return with id " + id + " deleted successfully");
    }

}
