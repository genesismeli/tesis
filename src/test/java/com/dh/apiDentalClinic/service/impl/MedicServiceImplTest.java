package com.dh.apiDentalClinic.service.impl;

import com.dh.apiDentalClinic.DTO.MedicDTO;
import com.dh.apiDentalClinic.service.IMedicService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class MedicServiceImplTest {

    @Autowired
    IMedicService dentistService;

    @Test
    public void testAddDentist() {

        MedicDTO medicDTO = new MedicDTO();
        medicDTO.setName("Test1");
        medicDTO.setLastName("Test2");
        dentistService.saveMedic(medicDTO);

        MedicDTO dentistTest = dentistService.findMedicById(1L);

        assertNotNull(dentistTest);
    }

}