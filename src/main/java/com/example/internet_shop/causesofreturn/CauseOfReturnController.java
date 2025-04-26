package com.example.internet_shop.causesofreturn;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/causes_of_return")
public class CauseOfReturnController {

    private final CauseOfReturnService causeOfReturnService;

    public CauseOfReturnController(CauseOfReturnService causeOfReturnService) {
        this.causeOfReturnService = causeOfReturnService;
    }

    @GetMapping("/")
    public ResponseEntity<List<CauseOfReturnDto>> getCausesOfReturn() {
        return ResponseEntity.ok(causeOfReturnService.getCausesOfReturn());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CauseOfReturnDto> getCauseOfReturnById(@PathVariable Long id) {
        return ResponseEntity.ok(causeOfReturnService.getCauseOfReturnById(id));
    }

    @PostMapping("/")
    public ResponseEntity<CauseOfReturnDto> createCauseOfReturn(@RequestBody CreateCauseOfReturnDto createCauseOfReturnDto) {
        CauseOfReturnDto createdCauseOfReturn = causeOfReturnService.createCauseOfReturn(createCauseOfReturnDto);
        return ResponseEntity.created(URI.create("/api/causes-of-return/" + createdCauseOfReturn.getCauseOfReturnId())).body(createdCauseOfReturn);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CauseOfReturnDto> updateCauseOfReturnById(@PathVariable Long id, @RequestBody CreateCauseOfReturnDto updateCauseOfReturnDto) {
        return ResponseEntity.ok(causeOfReturnService.updateCauseOfReturnById(id, updateCauseOfReturnDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCauseOfReturnById(@PathVariable Long id) {
        causeOfReturnService.deleteCauseOfReturnById(id);

        return ResponseEntity.ok("Deleted cause of return with id " + id);
    }

}
