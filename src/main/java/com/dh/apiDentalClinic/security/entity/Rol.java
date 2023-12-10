package com.dh.apiDentalClinic.security.entity;

import com.dh.apiDentalClinic.entity.Medic;
import com.dh.apiDentalClinic.security.enums.NameRol;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@Entity
@Table(name = "roles")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIdentityReference(alwaysAsId = true)
    @Enumerated(EnumType.STRING)
    private NameRol nameRol;



    public Rol() {
    }

    public Rol(NameRol nameRol) {
        this.nameRol = nameRol;
    }
}
