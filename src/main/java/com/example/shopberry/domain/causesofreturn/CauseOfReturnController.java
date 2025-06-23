package com.example.shopberry.domain.causesofreturn;

import com.example.shopberry.auth.access.CheckPermission;
import com.example.shopberry.domain.causesofreturn.dto.request.CreateCauseOfReturnRequestDto;
import com.example.shopberry.domain.causesofreturn.dto.request.UpdateCauseOfReturnRequestDto;
import com.example.shopberry.domain.causesofreturn.dto.response.CauseOfReturnResponseDto;
import com.example.shopberry.exception.ApiError;
import com.example.shopberry.user.Permission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get all causes of return")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of causes of return",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CauseOfReturnResponseDto.class)
                    )
            )
    })
    @GetMapping("/causes-of-return")
    public ResponseEntity<List<CauseOfReturnResponseDto>> getAllCausesOfReturn() {
        List<CauseOfReturnResponseDto> causeOfReturnResponseDtoList = causeOfReturnService.getAllCausesOfReturn();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(causeOfReturnResponseDtoList);
    }

    @Operation(summary = "Get cause of return by ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Cause of return found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CauseOfReturnResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cause of return not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @GetMapping("/causes-of-return/{causeOfReturnId}")
    public ResponseEntity<CauseOfReturnResponseDto> getCauseOfReturnById(@PathVariable Long causeOfReturnId) {
        CauseOfReturnResponseDto causeOfReturnResponseDto = causeOfReturnService.getCauseOfReturnById(causeOfReturnId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(causeOfReturnResponseDto);
    }

    @Operation(summary = "Create a new cause of return")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Cause of return created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CauseOfReturnResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Cause of return with that name already exists",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access Denied",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.CAUSE_OF_RETURN_CREATE)
    @PostMapping("/causes-of-return")
    public ResponseEntity<CauseOfReturnResponseDto> createCauseOfReturn(@Valid @RequestBody CreateCauseOfReturnRequestDto createCauseOfReturnRequestDto) {
        CauseOfReturnResponseDto createdCauseOfReturnResponseDto = causeOfReturnService.createCauseOfReturn(createCauseOfReturnRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/causes-of-return/" + createdCauseOfReturnResponseDto.getCauseOfReturnId()))
                .body(createdCauseOfReturnResponseDto);
    }

    @Operation(summary = "Update cause of return by ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Cause of return updated",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CauseOfReturnResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Cause of return with than name already exists",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access Denied",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cause of return not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.CAUSE_OF_RETURN_UPDATE)
    @PatchMapping("/causes-of-return/{causeOfReturnId}")
    public ResponseEntity<CauseOfReturnResponseDto> updateCauseOfReturnById(@PathVariable Long causeOfReturnId, @Valid @RequestBody UpdateCauseOfReturnRequestDto updateCauseOfReturnRequestDto) {
        CauseOfReturnResponseDto updatedCauseOfReturnResponseDto = causeOfReturnService.updateCauseOfReturnById(causeOfReturnId, updateCauseOfReturnRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedCauseOfReturnResponseDto);
    }

    @Operation(summary = "Delete cause of return by ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Cause of return deleted"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access Denied",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cause of return not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.CAUSE_OF_RETURN_DELETE)
    @DeleteMapping("/causes-of-return/{causeOfReturnId}")
    public ResponseEntity<Void> deleteCauseOfReturnById(@PathVariable Long causeOfReturnId) {
        causeOfReturnService.deleteCauseOfReturnById(causeOfReturnId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
