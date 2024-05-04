package com.obbutcheryproyecto.repository;

import com.obbutcheryproyecto.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface TaskRepository extends JpaRepository <Task,Long> {
    List<Task> findByDeliveryDateBetween( LocalDate startDate, LocalDate endDate);
}

