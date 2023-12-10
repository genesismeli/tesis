package com.dh.apiDentalClinic.service;


import com.dh.apiDentalClinic.DTO.MedicationDTO;
import com.dh.apiDentalClinic.entity.Medication;

import java.util.Collection;

public interface IMedicationService {

    Collection<MedicationDTO> findAllMedication();
    MedicationDTO findMedicationById(Long id);

    void saveMedication(MedicationDTO newMedicationDTO);

    void deleteMedication(Long id);

    void updateMedication(MedicationDTO newMedicationDTO);

    MedicationDTO convertEntityToDto(Medication medication);


}
