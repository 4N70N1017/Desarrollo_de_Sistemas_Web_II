package com.tiendasara.services;

import com.tiendasara.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByDescripcionContainingIgnoreCase(String descripcion);
    List<Product> findByCategoria_Id(Integer categoriaId);
    List<Product> findByMarca_Id(Integer marcaId);
}