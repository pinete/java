package com.obbutcheryproyecto.dto;

import com.obbutcheryproyecto.entities.Vat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// DTO evitando la asociación Artículos
public interface VatRepositoryDto extends JpaRepository<Vat,Long> {
    @Query("SELECT " +
                "v.id AS id, " +
                "v.taxName AS taxName, " +
                "v.ratePercent AS ratePercent, " +
                "v.surchargePercent AS surchargePercent, " +
                "v.applicationDate AS applicationDate, " +
                "v.codCountry AS codCountry " +
            "FROM Vat v")
    List<VatProjection> findAllDto();
}
