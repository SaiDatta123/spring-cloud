package com.wipro.controller;

import com.wipro.Service.PaymentService;
import com.wipro.entity.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@RestController
@RequestMapping("/pay")
@Tag(name = "Payment", description = "Payment management APIs")
public class PropertyController {

    @Autowired
    PaymentService paymentService;

    @PostMapping
    @Operation(summary = "Save a Payment", description = "Saves the payment object into the database.")
    public Payment addPayment(@RequestBody Payment payment) {
        return paymentService.savePayment(payment);
    }

    @GetMapping
    @Operation(summary = "Get all Payments", description = "Gets all payments present in the database.")
    public List<Payment> getAllPayments() {
        return paymentService.getAllPayments();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Payment ", description = "Gets a payment present in the database for the given Id.")
    public ResponseEntity<Payment> getPaymentById(@PathVariable int id) {
        return paymentService.getPaymentById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Payment ", description = "Deletes a payment present in the database for the given Id.")
    public void deletePayment(@PathVariable int id) {
        paymentService.deletePayment(id);
    }

    @PutMapping
    @Operation(summary = "Update a Payment ", description = "Updates a payment present in the database for the given Id, " +
            "else throw exception.")
    public Payment updatePayment(@RequestBody Payment payment) {
        return paymentService.updatePayment(payment);
    }

}
