package com.dh.apiDentalClinic;

import org.apache.log4j.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@SpringBootApplication
public class ApiDentalClinicApplication {

    public static void main(String[] args) {
        PropertyConfigurator.configure("log4j.properties");
        SpringApplication.run(ApiDentalClinicApplication.class, args);
    }

}
