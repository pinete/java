package com.example.obhibernateproyecto.dto;

import java.time.LocalDate;

public interface UserProjection {
    Long getId();
    String getFirstname();
    String getLastname();
    String getDni();
    Boolean getActive();
    LocalDate getBirthDate();



    // Agrega otros campos que necesites

}
