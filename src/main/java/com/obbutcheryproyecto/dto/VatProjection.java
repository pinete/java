package com.obbutcheryproyecto.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface VatProjection {
    Long getId();
    String getTaxName();
    Integer getRatePercent();
    BigDecimal getSurchargePercent();
    LocalDate getApplicationDate();
    Integer getCodCountry();
}
