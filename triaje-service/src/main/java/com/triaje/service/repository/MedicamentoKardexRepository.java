package com.triaje.service.repository;

import com.triaje.service.entity.MedicamentoKardex;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MedicamentoKardexRepository extends JpaRepository<MedicamentoKardex, UUID> {
}
