package com.dh.apiDentalClinic.repository;

import com.dh.apiDentalClinic.entity.Diagnosis;
import com.dh.apiDentalClinic.entity.PhysicalExam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPhysicalExamRepository extends JpaRepository<PhysicalExam, Long> {

}
