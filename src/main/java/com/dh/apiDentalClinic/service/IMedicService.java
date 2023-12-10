package com.dh.apiDentalClinic.service;

import com.dh.apiDentalClinic.DTO.MedicDTO;

import java.util.Collection;


public interface IMedicService {

    Collection<MedicDTO> findAllMedic();
    MedicDTO findMedicById(Long id);

    void saveMedic(MedicDTO newMedicDTO);

    void deleteMedic(Long id);

    void updateMedic(MedicDTO newMedicDTO);
}
