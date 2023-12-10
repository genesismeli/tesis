package com.dh.apiDentalClinic.entity;

import com.dh.apiDentalClinic.security.entity.Rol;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;


@ToString
@Setter
@Getter
@Entity
@Table(name = "medics")

@JsonIgnoreProperties(value={"hibernateLazyInitializer"})
public class Medic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    @NotBlank
    private String name;
    @Column
    @NotBlank
    private String lastName;
    @Column
    @NotBlank
    private String registrationNumber;
    @Column
    @NotBlank
    private String speciality;
    @Column
    @NotBlank
    private String email;
    @Column
    @NotBlank
    private String userName;
    @Column
    @NotBlank
    private String password;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "medic_rol", joinColumns = @JoinColumn(name = "medic_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private Rol rol;



}
