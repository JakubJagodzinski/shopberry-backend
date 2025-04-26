package com.example.internet_shop.paymenttypes;

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
    public ResponseEntity<List<PaymentTypeDto>> getPaymentTypes() {
        return ResponseEntity.ok(paymentTypeService.getPaymentTypes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentTypeDto> getPaymentTypeById(@PathVariable Long id) {
        return ResponseEntity.ok(paymentTypeService.getPaymentTypeById(id));
    }

    @PostMapping("/")
    public ResponseEntity<PaymentTypeDto> createPaymentType(@RequestBody CreatePaymentTypeDto createPaymentTypeDto) {
        PaymentTypeDto createdPaymentType = paymentTypeService.createPaymentType(createPaymentTypeDto);

        return ResponseEntity.created(URI.create("/api/payment_types/" + createdPaymentType.getPaymentTypeId())).body(createdPaymentType);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentTypeDto> updatePaymentTypeById(@PathVariable Long id, @RequestBody UpdatePaymentTypeDto updatePaymentTypeDto) {
        return ResponseEntity.ok(paymentTypeService.updatePaymentTypeById(id, updatePaymentTypeDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePaymentTypeById(@PathVariable Long id) {
        paymentTypeService.deletePaymentTypeById(id);

        return ResponseEntity.ok("Deleted payment type with id: " + id);
    }

}
