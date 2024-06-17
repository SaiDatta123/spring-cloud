package com.wipro.Service;

import com.wipro.Repository.PaymentRepo;
import com.wipro.entity.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    PaymentRepo paymentRepo;

    public Payment savePayment(Payment payment) {
        return paymentRepo.save(payment);
    }

    public List<Payment> getAllPayments() {
        return paymentRepo.findAll();
    }

    public Optional<Payment> getPaymentById(int id) {
        return paymentRepo.findById(id);
    }

    public void deletePayment(int id) {
        paymentRepo.deleteById(id);
    }

    public Payment updatePayment(Payment payment) {

        Optional<Payment> existingPayment = paymentRepo.findById(payment.getId());
        if (existingPayment.isPresent()) {
            if (payment.getBookingId() != null)
                existingPayment.get().setBookingId(payment.getBookingId());
            if (payment.getAmount() != null)
                existingPayment.get().setAmount(payment.getAmount());
            System.out.println("Payment to be updated : " + existingPayment.get());
            paymentRepo.save(existingPayment.get());
            return existingPayment.get();
        } else {
            throw new RuntimeException("Payment not found for the given id : " + payment.getId());
        }
    }


}
