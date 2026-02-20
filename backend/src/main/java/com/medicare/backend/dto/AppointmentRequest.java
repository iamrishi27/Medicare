package com.medicare.backend.dto;

import lombok.Data;

@Data
public class AppointmentRequest {
    private Long doctorId;
    private Long patientId;
    private String appointmentTime;
}
