package com.dh.apiDentalClinic.service.impl;

import com.dh.apiDentalClinic.DTO.DiagnosisDTO;
import com.dh.apiDentalClinic.entity.ClinicalRecord;
import com.dh.apiDentalClinic.entity.Diagnosis;
import com.dh.apiDentalClinic.entity.Patient;
import com.dh.apiDentalClinic.enums.DiagnosisStatus;
import com.dh.apiDentalClinic.exception.ResourceNotFoundException;
import com.dh.apiDentalClinic.repository.IClinicalRecordRepository;
import com.dh.apiDentalClinic.repository.IDiagnosisRepository;
import com.dh.apiDentalClinic.repository.IPatientRepository;
import com.dh.apiDentalClinic.service.IDiagnosisService;
import com.dh.apiDentalClinic.service.IPatientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DiagnosisServiceImpl implements IDiagnosisService {

    @Autowired
    private IDiagnosisRepository diagnosisRepository;

    @Autowired
    private IPatientRepository patientRepository;

    @Autowired
    private IClinicalRecordRepository clinicalRecordRepository;

    @Autowired
    IPatientService ipatientService;

    @Autowired
    ObjectMapper mapper;

    public void saveMethod(DiagnosisDTO diagnosisDTO) {
        if (diagnosisDTO != null) {
            Diagnosis diagnosis = mapper.convertValue(diagnosisDTO, Diagnosis.class);
            diagnosisRepository.save(diagnosis);
        } else {
            throw new ResourceNotFoundException("Diagnosis", "id", "id not found: " + diagnosisDTO.getId());
        }

    }

    @Override
    public Collection<DiagnosisDTO> findAllDiagnosis() {
        List<Diagnosis> diagnosis = diagnosisRepository.findAll();
        Set<DiagnosisDTO> diagnosisDTO = new HashSet<>();

        for (Diagnosis diag : diagnosis) {
            diagnosisDTO.add(mapper.convertValue(diag, DiagnosisDTO.class));
        }
        return diagnosisDTO;

    }

    @Override
    public DiagnosisDTO findDiagnosisById(Long id) {
        Diagnosis diag = diagnosisRepository.findById(id).get();
        DiagnosisDTO diagnosisDTO = null;
        if (diag != null) {
            diagnosisDTO = mapper.convertValue(diag, DiagnosisDTO.class);
        }
        return diagnosisDTO;
    }
    @Override
    public void saveDiagnosis(DiagnosisDTO newDiagnosisDTO) {
       saveMethod(newDiagnosisDTO);
    }




    @Override
    public void deleteDiagnosis(Long id) {
        Diagnosis diagnosis = diagnosisRepository.findById(id).get();
        diagnosisRepository.deleteById(id);
    }


    @Override
    public void updateDiagnosis(DiagnosisDTO newDiagnosisDTO) {
        saveMethod(newDiagnosisDTO);
    }

    @Override
    public DiagnosisDTO convertEntityToDto(Diagnosis diagnosis) {
        if (diagnosis == null) {
            return null;
        }

        DiagnosisDTO diagnosisDTO = new DiagnosisDTO();
        diagnosisDTO.setId(diagnosis.getId());
        diagnosisDTO.setCode(diagnosis.getCode());
        diagnosisDTO.setDescription(diagnosis.getDescription());
        diagnosisDTO.setStatus(diagnosis.getStatus());
        diagnosisDTO.setNotes(diagnosis.getNotes());



        return diagnosisDTO;
    }


}


