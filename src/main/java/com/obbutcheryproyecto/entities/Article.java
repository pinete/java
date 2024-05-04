package com.obbutcheryproyecto.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity () // Sin esta anotación, JPA no reconocerá la clase como una entidad gestionada.
@Table(name = "ob_article", indexes = @Index(columnList = "cod_art", unique = true))
@Data // Se crean automáticamente  ToString y Setters and Getters
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="cod_art",unique = true, length = 15)
    private String codArt;

    private String description;

    private BigDecimal price;

    @Column(name="tax_incl")
    private  Boolean ivaIncl;

    @Column (name = "created_date")
    private LocalDateTime createdDate;

    @Column (name = "updated_date")
    private LocalDateTime updatedDate;

    @Lob
    private byte[] image;

    // ASOCIOCIONES
    @JsonIgnoreProperties("articles")
    @ManyToOne
    @JoinColumn(name="tax_id")
    private Vat taxId;

    // Los Getters y Setters y el constructor básico son autogenerados mediante Lombok que implementa @Data

    // Para que en cada nuevo registro me guarde la fecha de creación y actualización automáticamente...
    @PrePersist
    public void prePersist() {
        if (this.createdDate == null) this.createdDate = LocalDateTime.now();
        if (this.updatedDate == null) this.updatedDate = LocalDateTime.now();
    }

// Para que en cada nueva actualización de registro me guarde la fecha de actualización automáticamente.
    @PreUpdate
    public void preUpdate() {
        this.updatedDate = LocalDateTime.now();
    }




}

