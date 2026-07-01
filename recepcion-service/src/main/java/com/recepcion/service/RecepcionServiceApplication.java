package com.recepcion.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.sql.DataSource;
import java.sql.Connection;

@SpringBootApplication
@EnableJpaAuditing
public class RecepcionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecepcionServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner logDatabaseInfo(DataSource dataSource) {
        return args -> {
            try (Connection conn = dataSource.getConnection()) {
                var meta = conn.getMetaData();
                System.out.println("=== DATABASE CONNECTED ===");
                System.out.println("Product: " + meta.getDatabaseProductName());
                System.out.println("URL: " + meta.getURL());
                System.out.println("User: " + meta.getUserName());
                System.out.println("==========================");
            }
        };
    }
}
