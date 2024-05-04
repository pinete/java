package com.obbutcheryproyecto.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Task: Representa una entidad Tarea, con los atributos id, título, descripción,
 * finalizada(S/N), fecha de entrega (LocalDate)
 */

@Entity
@Table(name="ob_task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String title;

    @Column(length = 300)
    private String description;

    private Boolean finished;

    @Column (name = "delivery_date")
    private LocalDate deliveryDate;

    @Column (name = "created_date")
    private LocalDateTime createdDate;

    @Column (name = "updated_date")
    private LocalDateTime updatedDate;

    // ASOCIACIONES
    @ManyToOne
    @JoinColumn(name="user_id")
    private User userId;

    // CONSTRUCTORES
    public Task() {}

    public Task(Long id, String title, String description, Boolean finished, LocalDate deliveryDate, User userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.finished = finished;
        this.deliveryDate = deliveryDate;
        this.userId = userId;
    }

    // GETTERS y SETTERS

    // Para que en cada nuevo registro me guarde la fecha de creación automáticamente.
    @PrePersist
    public void prePersist() {
        this.createdDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();
        if (this.finished == null) this.finished = false;
    }

    // Para que en cada nueva actualización de registro me guarde la fecha de actualización automáticamente.
    @PreUpdate
    public void preUpdate() {
        this.updatedDate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public LocalDateTime getCreatedDate() {return createdDate;}

    public void setCreatedDate(LocalDateTime createdDate) {this.createdDate = createdDate;}

    public LocalDateTime getUpdatedDate() {return updatedDate;}

    public void setUpdatedDate(LocalDateTime updatedDate) {this.updatedDate = updatedDate;}

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    // ToString
    // OJO. Omitimos las asociaciones para evitar llamadas en bucle
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", finished=" + finished +
                ", deliveryDate=" + deliveryDate +
                '}';
    }
}
