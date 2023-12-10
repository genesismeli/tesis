package com.dh.apiDentalClinic.service;

import com.dh.apiDentalClinic.DTO.ClinicalRecordDTO;
import com.dh.apiDentalClinic.DTO.PageDTO;
import com.dh.apiDentalClinic.entity.ClinicalRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Collection;
import java.util.List;

public interface IClinicalRecordService {

    PageDTO<ClinicalRecordDTO> findClinicalRecordsByPatientId(Long patientId, int page, int size, Sort sort);

    Collection<ClinicalRecordDTO> findAllClinicalRecord(Pageable pageable);

    ClinicalRecordDTO findClinicalRecordById(Long id);

    void saveClinicalRecord(ClinicalRecordDTO newClinicalRecordDTO);

    void deleteClinicalRecord(Long id);

    void updateClinicalRecord(ClinicalRecordDTO newClinicalRecordDTO);

    ClinicalRecordDTO convertEntityToDto(ClinicalRecord clinicalRecord);
}
