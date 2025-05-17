package com.example.shopberry.domain.paymenttypes;

import com.example.shopberry.domain.paymenttypes.dto.CreatePaymentTypeRequestDto;
import com.example.shopberry.domain.paymenttypes.dto.PaymentTypeResponseDto;
import com.example.shopberry.domain.paymenttypes.dto.UpdatePaymentTypeRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/payment-types")
public class PaymentTypeController {

    private final PaymentTypeService paymentTypeService;

    public PaymentTypeController(PaymentTypeService paymentTypeService) {
        this.paymentTypeService = paymentTypeService;
    }

    @GetMapping("/")
    public ResponseEntity<List<PaymentTypeResponseDto>> getPaymentTypes() {
        List<PaymentTypeResponseDto> paymentTypeResponseDtoList = paymentTypeService.getPaymentTypes();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(paymentTypeResponseDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentTypeResponseDto> getPaymentTypeById(@PathVariable Long id) {
        PaymentTypeResponseDto paymentTypeResponseDto = paymentTypeService.getPaymentTypeById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(paymentTypeResponseDto);
    }

    @PostMapping("/")
    public ResponseEntity<PaymentTypeResponseDto> createPaymentType(@RequestBody CreatePaymentTypeRequestDto createPaymentTypeRequestDto) {
        PaymentTypeResponseDto createdPaymentTypeResponseDto = paymentTypeService.createPaymentType(createPaymentTypeRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/payment-types/" + createdPaymentTypeResponseDto.getPaymentTypeId()))
                .body(createdPaymentTypeResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentTypeResponseDto> updatePaymentTypeById(@PathVariable Long id, @RequestBody UpdatePaymentTypeRequestDto updatePaymentTypeRequestDto) {
        PaymentTypeResponseDto updatedPaymentTypeResponseDto = paymentTypeService.updatePaymentTypeById(id, updatePaymentTypeRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedPaymentTypeResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePaymentTypeById(@PathVariable Long id) {
        paymentTypeService.deletePaymentTypeById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Payment type with id: " + id + " deleted successfully");
    }

}
