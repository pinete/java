package com.obbutcheryproyecto.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity()
@Table(name = "ob_vat", indexes = @Index(columnList = "tax_name",unique = true))
@Data
public class Vat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="tax_name")
    private String taxName; // Nombre del tipo de tasa (ej. "IVA21")

    @Column(name="rate_percent", length = 2)
    private Integer ratePercent; //porcentaje de Iva, VAT o similares

    @Column(name="surcharge_percent")
    private BigDecimal surchargePercent; // Porcentaje de Recargo de equivalencia o similares

    @Column(name="application_date")
    private LocalDate applicationDate; // Fecha a partir de la cual entran en vigor estos porcentajes

    @Column(name="cod_country")
    private Integer codCountry; // Código del pais al que pertenece el tipo de tasa

    // Asociaciones
    // Un impuesto puede estar asociado a muchos artículos
    @JsonIgnoreProperties("taxId")
    @OneToMany(mappedBy = "taxId")
    private List<Article> articles = new ArrayList<>();

}
