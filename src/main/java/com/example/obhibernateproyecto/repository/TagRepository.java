package com.example.obhibernateproyecto.repository;

import com.example.obhibernateproyecto.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag,Long> {

}
