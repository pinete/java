package com.obbutcheryproyecto.repository;

import com.obbutcheryproyecto.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article,Long> {
}

