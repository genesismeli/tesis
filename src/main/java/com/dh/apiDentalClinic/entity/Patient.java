package com.dh.apiDentalClinic.entity;


import com.dh.apiDentalClinic.enums.Gender;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@ToString
@Setter
@Getter
@Entity
@Table(name = "patients")

@JsonIgnoreProperties(value={"hibernateLazyInitializer"})
public class Patient {
    @Id
    @Column(name = "patient_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String lastName;
    @Column
    private String dni;
    @Column
    private Date birthdate;
    @Column
    private Gender gender;
    @Column
    private String adress;
    @Column
    private String phone;
    @Column
    private String email;


}
