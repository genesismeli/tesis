package com.dh.apiDentalClinic.service;

import com.dh.apiDentalClinic.DTO.PageDTO;
import com.dh.apiDentalClinic.DTO.PatientDTO;
import com.dh.apiDentalClinic.entity.ClinicalRecord;
import com.dh.apiDentalClinic.entity.Patient;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;


public interface IPatientService {


    PageDTO<PatientDTO> findAllPatients(int page, int size);

    PatientDTO findPatientById(Long id);

    void savePatient(PatientDTO newPatientDTO);

    void deletePatient(Long id);

    void updatePatient(PatientDTO newPatientDTO);

    PatientDTO convertEntityToDto(Patient patient);

    Patient findById(Long id);
}
