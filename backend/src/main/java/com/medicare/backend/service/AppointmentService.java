package com.medicare.backend.service;

import com.medicare.backend.dto.AppointmentRequest;
import com.medicare.backend.dto.AppointmentResponse;
import com.medicare.backend.model.Appointment;
import com.medicare.backend.model.Doctor;
import com.medicare.backend.model.Patient;
import com.medicare.backend.repository.AppointmentRepository;
import com.medicare.backend.repository.DoctorRepository;
import com.medicare.backend.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    // Helper to convert Appointment -> AppointmentResponse
    private AppointmentResponse toResponse(Appointment appointment) {
        AppointmentResponse response = new AppointmentResponse();
        response.setId(appointment.getId());
        response.setDoctorName(appointment.getDoctor().getName());
        response.setPatientName(appointment.getPatient().getName());
        response.setAppointmentTime(appointment.getAppointmentTime().toString());
        response.setStatus(appointment.getStatus());
        return response;
    }

    // GET all
    public List<AppointmentResponse> getAllAppointmentsResponse() {
        return appointmentRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // GET by id
    public Optional<AppointmentResponse> getAppointmentByIdResponse(Long id) {
        return appointmentRepository.findById(id).map(this::toResponse);
    }

    // CREATE
    public AppointmentResponse createAppointmentResponse(AppointmentRequest request) {
        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setAppointmentTime(LocalDateTime.parse(request.getAppointmentTime()));
        appointment.setStatus("PENDING");

        return toResponse(appointmentRepository.save(appointment));
    }

    // UPDATE
    public AppointmentResponse updateAppointmentResponse(Long id, AppointmentRequest request) {
        Appointment existing = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        existing.setDoctor(doctor);
        existing.setPatient(patient);
        existing.setAppointmentTime(LocalDateTime.parse(request.getAppointmentTime()));
        existing.setStatus("PENDING"); // or keep existing.getStatus()

        return toResponse(appointmentRepository.save(existing));
    }

    // DELETE
    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }

    // GET by doctor
    public List<AppointmentResponse> getAppointmentsByDoctorResponse(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // GET by patient
    public List<AppointmentResponse> getAppointmentsByPatientResponse(Long patientId) {
        return appointmentRepository.findByPatientId(patientId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}