package com.dh.apiDentalClinic.DTO;

import com.dh.apiDentalClinic.entity.Patient;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ClinicalRecordDTO {
    private Long id;
    private Date date;
    private PatientDTO patient;
    private List<PhysicalExamDTO> physicalExams;
    private List<MedicationDTO> medications;
    private List<DiagnosisDTO> diagnoses;
}

