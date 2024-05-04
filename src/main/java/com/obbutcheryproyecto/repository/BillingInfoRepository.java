package com.obbutcheryproyecto.repository;

import com.obbutcheryproyecto.entities.BillingInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingInfoRepository extends JpaRepository<BillingInfo,Long> {
}
