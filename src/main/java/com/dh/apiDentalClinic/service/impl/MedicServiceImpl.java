package com.dh.apiDentalClinic.service.impl;

import com.dh.apiDentalClinic.DTO.MedicDTO;
import com.dh.apiDentalClinic.DTO.PatientDTO;
import com.dh.apiDentalClinic.entity.Medic;
import com.dh.apiDentalClinic.entity.Patient;
import com.dh.apiDentalClinic.exception.ResourceNotFoundException;
import com.dh.apiDentalClinic.repository.IMedicRepository;
import com.dh.apiDentalClinic.service.IMedicService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MedicServiceImpl implements IMedicService {

    @Autowired
    private IMedicRepository medicRepository;

    @Autowired
    ObjectMapper mapper;

    public void saveMethod(MedicDTO medicDTO) {
        if (medicDTO != null) {
            Medic medic = mapper.convertValue(medicDTO, Medic.class);
            medicRepository.save(medic);
        } else {
            throw new ResourceNotFoundException("Medic", "id", "id not found: " + medicDTO.getId());
        }

    }

    @Override
    public Collection<MedicDTO> findAllMedic() {
        List<Medic> medics = medicRepository.findAll();
        Set<MedicDTO> medicDTO = new HashSet<>();

        for (Medic medic : medics) {
            medicDTO.add(mapper.convertValue(medic, MedicDTO.class));
        }
        return medicDTO;

    }


    @Override
    public MedicDTO findMedicById(Long id) {
        Medic medic = medicRepository.findById(id).get();
        MedicDTO medicDTO = null;
        if (medic != null) {
            medicDTO = mapper.convertValue(medic, MedicDTO.class);
        }
        return medicDTO;
    }

    @Override
    public void saveMedic(MedicDTO newMedicDTO) {
        saveMethod(newMedicDTO);
    }

    @Override
    public void deleteMedic(Long id) {
        Medic medic = medicRepository.findById(id).get();
        medicRepository.deleteById(id);


    }

    @Override
    public void updateMedic(MedicDTO newMedicDTO) {
        saveMethod(newMedicDTO);
    }
}
