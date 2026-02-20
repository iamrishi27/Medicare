package com.medicare.backend.service;

import com.medicare.backend.model.Patient;
import com.medicare.backend.repository.PatientRepository;
import com.medicare.backend.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    public Patient addPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public Patient getPatientById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient updatePatient(Long id, Patient patient) {
        Patient existing = getPatientById(id);
        existing.setName(patient.getName());
        existing.setEmail(patient.getEmail());
        existing.setPhone(patient.getPhone());
        return patientRepository.save(existing);
    }

    public void deletePatient(Long id) {
        Patient existing = getPatientById(id);
        patientRepository.delete(existing);
    }
}