package com.example.internet_shop.paymenttypes;

import com.example.internet_shop.paymenttypes.dto.CreatePaymentTypeRequestDto;
import com.example.internet_shop.paymenttypes.dto.PaymentTypeResponseDto;
import com.example.internet_shop.paymenttypes.dto.UpdatePaymentTypeRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/payment_types")
public class PaymentTypeController {

    private final PaymentTypeService paymentTypeService;

    public PaymentTypeController(PaymentTypeService paymentTypeService) {
        this.paymentTypeService = paymentTypeService;
    }

    @GetMapping("/")
    public ResponseEntity<List<PaymentTypeResponseDto>> getPaymentTypes() {
        return ResponseEntity.ok(paymentTypeService.getPaymentTypes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentTypeResponseDto> getPaymentTypeById(@PathVariable Long id) {
        return ResponseEntity.ok(paymentTypeService.getPaymentTypeById(id));
    }

    @PostMapping("/")
    public ResponseEntity<PaymentTypeResponseDto> createPaymentType(@RequestBody CreatePaymentTypeRequestDto createPaymentTypeRequestDto) {
        PaymentTypeResponseDto createdPaymentType = paymentTypeService.createPaymentType(createPaymentTypeRequestDto);

        return ResponseEntity.created(URI.create("/api/payment_types/" + createdPaymentType.getPaymentTypeId())).body(createdPaymentType);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentTypeResponseDto> updatePaymentTypeById(@PathVariable Long id, @RequestBody UpdatePaymentTypeRequestDto updatePaymentTypeRequestDto) {
        return ResponseEntity.ok(paymentTypeService.updatePaymentTypeById(id, updatePaymentTypeRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePaymentTypeById(@PathVariable Long id) {
        paymentTypeService.deletePaymentTypeById(id);

        return ResponseEntity.ok("Deleted payment type with id: " + id);
    }

}
