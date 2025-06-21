package com.example.shopberry.domain.paymenttypes;

import com.example.shopberry.auth.access.CheckPermission;
import com.example.shopberry.common.MessageResponseDto;
import com.example.shopberry.domain.paymenttypes.dto.CreatePaymentTypeRequestDto;
import com.example.shopberry.domain.paymenttypes.dto.PaymentTypeResponseDto;
import com.example.shopberry.domain.paymenttypes.dto.UpdatePaymentTypeRequestDto;
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
public class PaymentTypeController {

    private final PaymentTypeService paymentTypeService;

    @CheckPermission(Permission.PAYMENT_TYPE_READ_ALL)
    @GetMapping("/payment-types")
    public ResponseEntity<List<PaymentTypeResponseDto>> getAllPaymentTypes() {
        List<PaymentTypeResponseDto> paymentTypeResponseDtoList = paymentTypeService.getAllPaymentTypes();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(paymentTypeResponseDtoList);
    }

    @CheckPermission(Permission.PAYMENT_TYPE_READ)
    @GetMapping("/payment-types/{paymentTypeId}")
    public ResponseEntity<PaymentTypeResponseDto> getPaymentTypeById(@PathVariable Long paymentTypeId) {
        PaymentTypeResponseDto paymentTypeResponseDto = paymentTypeService.getPaymentTypeById(paymentTypeId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(paymentTypeResponseDto);
    }

    @CheckPermission(Permission.PAYMENT_TYPE_CREATE)
    @PostMapping("/payment-types")
    public ResponseEntity<PaymentTypeResponseDto> createPaymentType(@Valid @RequestBody CreatePaymentTypeRequestDto createPaymentTypeRequestDto) {
        PaymentTypeResponseDto createdPaymentTypeResponseDto = paymentTypeService.createPaymentType(createPaymentTypeRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/payment-types/" + createdPaymentTypeResponseDto.getPaymentTypeId()))
                .body(createdPaymentTypeResponseDto);
    }

    @CheckPermission(Permission.PAYMENT_TYPE_UPDATE)
    @PatchMapping("/payment-types/{paymentTypeId}")
    public ResponseEntity<PaymentTypeResponseDto> updatePaymentTypeById(@PathVariable Long paymentTypeId, @Valid @RequestBody UpdatePaymentTypeRequestDto updatePaymentTypeRequestDto) {
        PaymentTypeResponseDto updatedPaymentTypeResponseDto = paymentTypeService.updatePaymentTypeById(paymentTypeId, updatePaymentTypeRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedPaymentTypeResponseDto);
    }

    @CheckPermission(Permission.PAYMENT_TYPE_DELETE)
    @DeleteMapping("/payment-types/{paymentTypeId}")
    public ResponseEntity<MessageResponseDto> deletePaymentTypeById(@PathVariable Long paymentTypeId) {
        paymentTypeService.deletePaymentTypeById(paymentTypeId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Payment type with id: " + paymentTypeId + " deleted successfully"));
    }

}
