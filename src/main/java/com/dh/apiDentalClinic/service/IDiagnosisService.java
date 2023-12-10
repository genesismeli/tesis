package com.dh.apiDentalClinic.service;

import com.dh.apiDentalClinic.DTO.DiagnosisDTO;
import com.dh.apiDentalClinic.entity.Diagnosis;

import java.util.Collection;

public interface IDiagnosisService {
    Collection<DiagnosisDTO> findAllDiagnosis();

    DiagnosisDTO findDiagnosisById(Long id);

    void saveDiagnosis(DiagnosisDTO newDiagnosisDTO);

    void deleteDiagnosis(Long id);

    void updateDiagnosis(DiagnosisDTO newDiagnosisDTO);

    DiagnosisDTO convertEntityToDto(Diagnosis diagnosis);



}
