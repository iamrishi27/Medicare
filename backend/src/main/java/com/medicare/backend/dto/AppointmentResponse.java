package com.medicare.backend.dto;

import lombok.Data;

@Data
public class AppointmentResponse {
    private Long id;
    private String doctorName;
    private String patientName;
    private String appointmentTime;
    private String status;
}
