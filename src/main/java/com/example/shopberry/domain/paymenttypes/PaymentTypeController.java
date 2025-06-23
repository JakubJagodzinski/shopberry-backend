package com.example.shopberry.domain.paymenttypes;

import com.example.shopberry.auth.access.CheckPermission;
import com.example.shopberry.domain.paymenttypes.dto.request.CreatePaymentTypeRequestDto;
import com.example.shopberry.domain.paymenttypes.dto.request.UpdatePaymentTypeRequestDto;
import com.example.shopberry.domain.paymenttypes.dto.response.PaymentTypeResponseDto;
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
public class PaymentTypeController {

    private final PaymentTypeService paymentTypeService;

    @Operation(summary = "Get all payment types")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of payment types",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PaymentTypeResponseDto.class)
                    )
            )
    })
    @GetMapping("/payment-types")
    public ResponseEntity<List<PaymentTypeResponseDto>> getAllPaymentTypes() {
        List<PaymentTypeResponseDto> paymentTypeResponseDtoList = paymentTypeService.getAllPaymentTypes();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(paymentTypeResponseDtoList);
    }

    @Operation(summary = "Get payment type by id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Payment type found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PaymentTypeResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Payment type not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @GetMapping("/payment-types/{paymentTypeId}")
    public ResponseEntity<PaymentTypeResponseDto> getPaymentTypeById(@PathVariable Long paymentTypeId) {
        PaymentTypeResponseDto paymentTypeResponseDto = paymentTypeService.getPaymentTypeById(paymentTypeId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(paymentTypeResponseDto);
    }

    @Operation(summary = "Create a new payment type")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Payment type created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PaymentTypeResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Payment type with that name already exists",
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
    @CheckPermission(Permission.PAYMENT_TYPE_CREATE)
    @PostMapping("/payment-types")
    public ResponseEntity<PaymentTypeResponseDto> createPaymentType(@Valid @RequestBody CreatePaymentTypeRequestDto createPaymentTypeRequestDto) {
        PaymentTypeResponseDto createdPaymentTypeResponseDto = paymentTypeService.createPaymentType(createPaymentTypeRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/payment-types/" + createdPaymentTypeResponseDto.getPaymentTypeId()))
                .body(createdPaymentTypeResponseDto);
    }

    @Operation(summary = "Update payment type by ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Payment type updated",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PaymentTypeResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Payment type with that name already exists",
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
                    description = "Payment type not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.PAYMENT_TYPE_UPDATE)
    @PatchMapping("/payment-types/{paymentTypeId}")
    public ResponseEntity<PaymentTypeResponseDto> updatePaymentTypeById(@PathVariable Long paymentTypeId, @Valid @RequestBody UpdatePaymentTypeRequestDto updatePaymentTypeRequestDto) {
        PaymentTypeResponseDto updatedPaymentTypeResponseDto = paymentTypeService.updatePaymentTypeById(paymentTypeId, updatePaymentTypeRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedPaymentTypeResponseDto);
    }

    @Operation(summary = "Delete payment type by ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Payment type deleted"
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
                    description = "Payment type not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.PAYMENT_TYPE_DELETE)
    @DeleteMapping("/payment-types/{paymentTypeId}")
    public ResponseEntity<Void> deletePaymentTypeById(@PathVariable Long paymentTypeId) {
        paymentTypeService.deletePaymentTypeById(paymentTypeId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
