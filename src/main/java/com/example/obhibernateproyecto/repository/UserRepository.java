package com.example.obhibernateproyecto.repository;

import com.example.obhibernateproyecto.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    List<User> findByBirthDateBetween(LocalDate startDate, LocalDate endDate);
}
