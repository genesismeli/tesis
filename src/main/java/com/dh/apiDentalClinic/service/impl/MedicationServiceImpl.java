package com.dh.apiDentalClinic.service.impl;

import com.dh.apiDentalClinic.DTO.DiagnosisDTO;
import com.dh.apiDentalClinic.DTO.MedicationDTO;
import com.dh.apiDentalClinic.entity.Diagnosis;
import com.dh.apiDentalClinic.entity.Medication;
import com.dh.apiDentalClinic.exception.ResourceNotFoundException;
import com.dh.apiDentalClinic.repository.IMedicationRepository;
import com.dh.apiDentalClinic.service.IMedicationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class MedicationServiceImpl implements IMedicationService {

    @Autowired
    private IMedicationRepository medicationRepository;

    @Autowired
    ObjectMapper mapper;

    public void saveMethod(MedicationDTO medicationDTO) {
        if (medicationDTO != null) {
            Medication medication = mapper.convertValue(medicationDTO, Medication.class);
            medicationRepository.save(medication);
        } else {
            throw new ResourceNotFoundException("Medication", "id", "id not found: " + medicationDTO.getId());
        }

    }

    @Override
    public Collection<MedicationDTO> findAllMedication() {
        List<Medication> medications = medicationRepository.findAll();
        Set<MedicationDTO> medicationDTO = new HashSet<>();

        for (Medication medication : medications) {
            medicationDTO.add(mapper.convertValue(medication, MedicationDTO.class));
        }
        return medicationDTO;

    }

    @Override
    public MedicationDTO findMedicationById(Long id) {
        Medication medication = medicationRepository.findById(id).get();
        MedicationDTO medicationDTO = null;
        if (medication != null) {
            medicationDTO = mapper.convertValue(medication, MedicationDTO.class);
        }
        return medicationDTO;
    }

    @Override
    public void saveMedication(MedicationDTO newMedicationDTO) {
        saveMethod(newMedicationDTO);
    }

    @Override
    public void deleteMedication(Long id) {
        Medication medication = medicationRepository.findById(id).get();
        medicationRepository.deleteById(id);

    }

    @Override
    public void updateMedication(MedicationDTO newMedicationDTO) {
        saveMethod(newMedicationDTO);
    }

    @Override
    public MedicationDTO convertEntityToDto(Medication medication) {
        if (medication == null) {
            return null;
        }

        MedicationDTO medicationDTO = new MedicationDTO();
        medicationDTO.setId(medication.getId());
        medicationDTO.setMedicationName(medication.getMedicationName());
        medicationDTO.setConcentration(medication.getConcentration());
        medicationDTO.setPresentation(medication.getPresentation());
        medicationDTO.setTradeName(medication.getTradeName());

        return medicationDTO;
    }


}
