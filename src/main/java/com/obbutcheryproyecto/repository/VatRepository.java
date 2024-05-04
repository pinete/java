package com.obbutcheryproyecto.repository;

import com.obbutcheryproyecto.entities.Vat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VatRepository extends JpaRepository<Vat,Long> {

}

