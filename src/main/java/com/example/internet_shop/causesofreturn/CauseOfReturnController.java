package com.example.internet_shop.causesofreturn;

import com.example.internet_shop.causesofreturn.dto.CauseOfReturnResponseDto;
import com.example.internet_shop.causesofreturn.dto.CreateCauseOfReturnRequestDto;
import com.example.internet_shop.causesofreturn.dto.UpdateCauseOfReturnRequestDto;
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
        return ResponseEntity.ok(causeOfReturnService.getCausesOfReturn());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CauseOfReturnResponseDto> getCauseOfReturnById(@PathVariable Long id) {
        return ResponseEntity.ok(causeOfReturnService.getCauseOfReturnById(id));
    }

    @PostMapping("/")
    public ResponseEntity<CauseOfReturnResponseDto> createCauseOfReturn(@RequestBody CreateCauseOfReturnRequestDto createCauseOfReturnRequestDto) {
        CauseOfReturnResponseDto createdCauseOfReturn = causeOfReturnService.createCauseOfReturn(createCauseOfReturnRequestDto);
        return ResponseEntity.created(URI.create("/api/v1/causes-of-return/" + createdCauseOfReturn.getCauseOfReturnId())).body(createdCauseOfReturn);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CauseOfReturnResponseDto> updateCauseOfReturnById(@PathVariable Long id, @RequestBody UpdateCauseOfReturnRequestDto updateCauseOfReturnRequestDto) {
        return ResponseEntity.ok(causeOfReturnService.updateCauseOfReturnById(id, updateCauseOfReturnRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCauseOfReturnById(@PathVariable Long id) {
        causeOfReturnService.deleteCauseOfReturnById(id);

        return ResponseEntity.ok("Deleted cause of return with id " + id);
    }

}
