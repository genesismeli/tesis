package com.dh.apiDentalClinic.repository;

import com.dh.apiDentalClinic.entity.ClinicalRecord;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IClinicalRecordRepository extends JpaRepository<ClinicalRecord, Long> {
    Page<ClinicalRecord> findByPatientId(Long patientId, Pageable pageable);
    }



