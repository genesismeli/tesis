package com.dh.apiDentalClinic.repository;

import com.dh.apiDentalClinic.entity.Diagnosis;
import com.dh.apiDentalClinic.entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IMedicationRepository extends JpaRepository<Medication, Long> {



}
