package com.dh.apiDentalClinic.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "medication")
@JsonIgnoreProperties(value={"hibernateLazyInitializer"})
public class Medication {

    @Id
    @Column(name = "medication_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(name = "medication_name") //nombre de la droga
    private String medicationName;

    @Column(name = "concentration")
    private String concentration;

    @Column(name = "presentation")
    private String presentation;

    @Column(name = "trade_name") // nombre comercial
    private String tradeName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinical_records_id")
    private ClinicalRecord clinicalRecord;


}
