package com.example.obhibernateproyecto.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Representa una entidad con los siguientes atributos:
 * id, nombre, apellido, dni, si esta activo o no, fecha de nacimiento
 */
@Entity
@Table(name = "ob_users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "first_name", length = 30, nullable=false)
    private String firstname;

    @Column(name="last_name")
    private String lastname;

    @Column(nullable=false, unique = true)
    private String dni;

    private Boolean active;

    @Column(name="birth_date")
    private LocalDate birthDate;


    // ASOCIACIONES
    /*
    Un usuario tiene una información de facturación (BillingInfo).
    Una información de facturación solo puede pertenecer a un mismo usuario.
    Por tanto, asociación tipo OneToOne
     */
    // Para evitar la recursion con las asociaciones en dos sentidos podemos usar @JsonIgnoreProperties("nombreDeLaPropiedadEnBillingInfo")
    // Esto logra que al llamar al usuario buscará la factura, pero al mostrar la factura no provoca la recursion al usuario.
    @JsonIgnoreProperties("user")
    @OneToOne
    @JoinColumn(name="billing_info_id", unique=true)
    private BillingInfo billingInfo;

    /*
    Un usuario tiene muchas tareas. One to Many
    Una tarea solo puede pertenecer a un mismo usuario a la vez
     */
    // Para evitar la recursion con las asociaciones en dos sentidos podemos usar @JsonIgnoreProperties("nombreDeLaPropiedadEnTask")
    // Esto logra que al llamar al usuario buscará la tarea, pero al mostrar la tarea no provoca la recursion al usuario.
    @JsonIgnoreProperties("userId")
    @OneToMany(mappedBy = "userId")
    private List<Task> tasks = new ArrayList<>();


    // CONSTRUCTORES
    public User(){}

    public User(Long id, LocalDate birthDate, Boolean active, String dni, String lastname, String firstname) {
        this.id = id;
        this.birthDate = birthDate;
        this.active = active;
        this.dni = dni;
        this.lastname = lastname;
        this.firstname = firstname;
    }

    //GETTERS Y SETTERS
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public BillingInfo getBillingInfo() {
        return billingInfo;
    }

    public void setBillingInfo(BillingInfo billingInfo) {
        this.billingInfo = billingInfo;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    // ToString
    // OJO. Omitimos las asociaciones para evitar llamadas en bucle
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", dni='" + dni + '\'' +
                ", active=" + active +
                ", birthDate=" + birthDate +
                '}';
    }


}