package com.dh.apiDentalClinic.service;

import com.dh.apiDentalClinic.DTO.PatientDTO;
import com.dh.apiDentalClinic.DTO.PhysicalExamDTO;
import com.dh.apiDentalClinic.entity.PhysicalExam;

import java.util.Collection;


public interface IPhysicalExamService {

        Collection<PhysicalExamDTO> findAllPhysicalExam();

        PhysicalExamDTO findPhysicalExamById(Long id);

        void savePhysicalExam(PhysicalExamDTO newPhysicalExamDTO);

        void deletePhysicalExam(Long id);

        void updatePhysicalExam(PhysicalExamDTO newPhysicalExamDTO);

        PhysicalExamDTO convertEntityToDto(PhysicalExam physicalExam);

}




