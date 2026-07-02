package com.triaje.service.repository;

import com.triaje.service.entity.SignosVitales;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SignosVitalesRepository extends JpaRepository<SignosVitales, UUID> {
}
