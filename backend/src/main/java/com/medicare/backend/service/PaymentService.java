package com.medicare.backend.service;

import com.medicare.backend.model.Appointment;
import com.medicare.backend.model.Payment;
import com.medicare.backend.repository.AppointmentRepository;
import com.medicare.backend.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final AppointmentRepository appointmentRepository;

    public Payment makePayment(Long appointmentId, Double amount) {

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        Payment payment = new Payment();
        payment.setAppointment(appointment);
        payment.setAmount(amount);
        payment.setStatus("PAID");

        return paymentRepository.save(payment);
    }
}
