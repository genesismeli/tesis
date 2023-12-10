package com.dh.apiDentalClinic.repository;

import com.dh.apiDentalClinic.entity.Medic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMedicRepository extends JpaRepository<Medic, Long> {
}
