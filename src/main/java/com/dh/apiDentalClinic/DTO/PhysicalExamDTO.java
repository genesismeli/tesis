package com.dh.apiDentalClinic.DTO;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;

@Data
@NoArgsConstructor
@Getter
@Setter
public class PhysicalExamDTO {

    private Long id;

    private Integer heartRate; // Frecuencia Cardiaca

    @DecimalMin(value = "0.0", message = "La saturación de oxígeno no puede ser menor a 0")
    private Double oxygenSaturation; //Saturacion parcial de Oxigeno

    private Integer respiratoryRate; // Frecuencia Respiratoria

    private Double systolicPressure; // Presion Sistolica

    private Double diastolicPressure; // Presion Diastólica

    private Integer beatsPerMinute; //Latidos por minuto

    @DecimalMin(value = "0.0", message = "La glucosa no puede ser menor a 0")
    private Double glucose; // Glucosa

}
