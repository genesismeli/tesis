package com.dh.apiDentalClinic.DTO;

import com.dh.apiDentalClinic.entity.Patient;
import com.dh.apiDentalClinic.enums.DiagnosisStatus;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Data
@NoArgsConstructor
@Getter
@Setter
public class DiagnosisDTO {

    private Long id;
    private String code;
    private String description;
    private DiagnosisStatus status;
    private String notes;


}
