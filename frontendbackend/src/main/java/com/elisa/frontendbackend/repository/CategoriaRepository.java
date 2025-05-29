package com.elisa.frontendbackend.repository;

import com.elisa.frontendbackend.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
