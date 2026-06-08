package com.tiendasara.services;

import com.tiendasara.models.Mark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarkRepository extends JpaRepository<Mark, Integer> {
    Mark findByDescripcion(String descripcion);
}