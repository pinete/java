package com.obbutcheryproyecto.dto;

import com.obbutcheryproyecto.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface UserRepositoryDto extends JpaRepository<User, Long> {
    @Query("SELECT " +
                "u.id AS id, " +
                "u.firstname AS firstname, " +
                "u.lastname AS lastname, " +
                "u.dni AS dni, " +
                "u.active AS active, " +
                "u.birthDate AS birthDate " +
            "FROM User u")
    List<UserProjection> findAllDto();
}
