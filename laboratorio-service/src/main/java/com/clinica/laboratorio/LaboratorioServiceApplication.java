package com.clinica.laboratorio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class LaboratorioServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(LaboratorioServiceApplication.class, args);
    }
}
