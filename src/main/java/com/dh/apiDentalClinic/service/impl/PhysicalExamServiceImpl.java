package com.dh.apiDentalClinic.service.impl;

import com.dh.apiDentalClinic.DTO.PatientDTO;
import com.dh.apiDentalClinic.DTO.PhysicalExamDTO;
import com.dh.apiDentalClinic.entity.Patient;
import com.dh.apiDentalClinic.entity.PhysicalExam;
import com.dh.apiDentalClinic.exception.ResourceNotFoundException;
import com.dh.apiDentalClinic.repository.IPhysicalExamRepository;
import com.dh.apiDentalClinic.service.IPhysicalExamService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PhysicalExamServiceImpl implements IPhysicalExamService {

    @Autowired
    private IPhysicalExamRepository physicalExamRepository;

    @Autowired
    ObjectMapper mapper;

    public void saveMethod(PhysicalExamDTO physicalExamDTO) {
        if (physicalExamDTO != null) {
            PhysicalExam physicalExam = mapper.convertValue(physicalExamDTO, PhysicalExam.class);
            physicalExamRepository.save(physicalExam);
        } else {
            throw new ResourceNotFoundException("PhysicalExam", "id", "id not found: " + physicalExamDTO.getId());
        }

    }


    @Override
    public Collection<PhysicalExamDTO> findAllPhysicalExam() {
        List<PhysicalExam> physicalExams = physicalExamRepository.findAll();
        Set<PhysicalExamDTO> physicalExamDTO = new HashSet<>();
        for (PhysicalExam physicalExam : physicalExams) {
            physicalExamDTO.add(mapper.convertValue(physicalExams, PhysicalExamDTO.class));
        }
        return physicalExamDTO;

    }


    @Override
    public PhysicalExamDTO findPhysicalExamById(Long id) {

        PhysicalExam physicalExam = physicalExamRepository.findById(id).get();
        PhysicalExamDTO physicalExamDTO = null;
        if (physicalExam != null) {
            physicalExamDTO = mapper.convertValue(physicalExam, PhysicalExamDTO
                    .class);
        }
        return physicalExamDTO;
    }

    public void savePhysicalExam(PhysicalExamDTO newPhysicalExamDTO) {
        saveMethod(newPhysicalExamDTO);
    }

    @Override
    public void deletePhysicalExam(Long id) {
        PhysicalExam physicalExam = physicalExamRepository.findById(id).get();
        physicalExamRepository.deleteById(id);
    }

    @Override
    public void updatePhysicalExam(PhysicalExamDTO newPhysicalExamDTO) {
        saveMethod(newPhysicalExamDTO);
    }

    @Override
    public PhysicalExamDTO convertEntityToDto(PhysicalExam physicalExam) {
        if (physicalExam == null) {
            return null;
        }

        PhysicalExamDTO physicalExamDTO = new PhysicalExamDTO();
        physicalExamDTO.setId(physicalExam.getId());
        physicalExamDTO.setHeartRate(physicalExam.getHeartRate());
        physicalExamDTO.setOxygenSaturation(physicalExam.getOxygenSaturation());
        physicalExamDTO.setRespiratoryRate(physicalExam.getRespiratoryRate());
        physicalExamDTO.setSystolicPressure(physicalExam.getSystolicPressure());
        physicalExamDTO.setDiastolicPressure(physicalExam.getDiastolicPressure());
        physicalExamDTO.setBeatsPerMinute(physicalExam.getBeatsPerMinute());
        physicalExamDTO.setGlucose(physicalExam.getGlucose());

        return physicalExamDTO;
    }


}



