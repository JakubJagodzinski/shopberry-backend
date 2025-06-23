package com.example.shopberry.domain.causesofreturn;

import com.example.shopberry.auth.access.CheckPermission;
import com.example.shopberry.domain.causesofreturn.dto.request.CreateCauseOfReturnRequestDto;
import com.example.shopberry.domain.causesofreturn.dto.request.UpdateCauseOfReturnRequestDto;
import com.example.shopberry.domain.causesofreturn.dto.response.CauseOfReturnResponseDto;
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
public class CauseOfReturnController {

    private final CauseOfReturnService causeOfReturnService;

    @GetMapping("/causes-of-return")
    public ResponseEntity<List<CauseOfReturnResponseDto>> getAllCausesOfReturn() {
        List<CauseOfReturnResponseDto> causeOfReturnResponseDtoList = causeOfReturnService.getAllCausesOfReturn();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(causeOfReturnResponseDtoList);
    }

    @GetMapping("/causes-of-return/{causeOfReturnId}")
    public ResponseEntity<CauseOfReturnResponseDto> getCauseOfReturnById(@PathVariable Long causeOfReturnId) {
        CauseOfReturnResponseDto causeOfReturnResponseDto = causeOfReturnService.getCauseOfReturnById(causeOfReturnId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(causeOfReturnResponseDto);
    }

    @CheckPermission(Permission.CAUSE_OF_RETURN_CREATE)
    @PostMapping("/causes-of-return")
    public ResponseEntity<CauseOfReturnResponseDto> createCauseOfReturn(@Valid @RequestBody CreateCauseOfReturnRequestDto createCauseOfReturnRequestDto) {
        CauseOfReturnResponseDto createdCauseOfReturnResponseDto = causeOfReturnService.createCauseOfReturn(createCauseOfReturnRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/causes-of-return/" + createdCauseOfReturnResponseDto.getCauseOfReturnId()))
                .body(createdCauseOfReturnResponseDto);
    }

    @CheckPermission(Permission.CAUSE_OF_RETURN_UPDATE)
    @PatchMapping("/causes-of-return/{causeOfReturnId}")
    public ResponseEntity<CauseOfReturnResponseDto> updateCauseOfReturnById(@PathVariable Long causeOfReturnId, @Valid @RequestBody UpdateCauseOfReturnRequestDto updateCauseOfReturnRequestDto) {
        CauseOfReturnResponseDto updatedCauseOfReturnResponseDto = causeOfReturnService.updateCauseOfReturnById(causeOfReturnId, updateCauseOfReturnRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedCauseOfReturnResponseDto);
    }

    @CheckPermission(Permission.CAUSE_OF_RETURN_DELETE)
    @DeleteMapping("/causes-of-return/{causeOfReturnId}")
    public ResponseEntity<Void> deleteCauseOfReturnById(@PathVariable Long causeOfReturnId) {
        causeOfReturnService.deleteCauseOfReturnById(causeOfReturnId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
