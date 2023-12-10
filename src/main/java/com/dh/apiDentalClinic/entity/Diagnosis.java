package com.dh.apiDentalClinic.entity;

import com.dh.apiDentalClinic.converters.DiagnosisStatusConverter;
import com.dh.apiDentalClinic.enums.DiagnosisStatus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "diagnosis") // Nombre de la tabla en la base de datos
public class Diagnosis {

    @Id
    @JoinColumn(name = "diagnosis_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "description")
    private String description;

    @Convert(converter = DiagnosisStatusConverter.class)
    @Column(name = "status")
    private DiagnosisStatus status;

    @Column(name = "notes")
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinical_records_id")
    private ClinicalRecord clinicalRecord;

}

