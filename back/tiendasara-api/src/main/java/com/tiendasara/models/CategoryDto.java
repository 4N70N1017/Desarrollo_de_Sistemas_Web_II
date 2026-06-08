package com.tiendasara.models;

import jakarta.validation.constraints.NotBlank;

public class CategoryDto {
    
    private Integer id;
    
    @NotBlank(message = "La descripción no puede estar vacía")
    private String descripcion;
    
    public CategoryDto() {}
    
    public CategoryDto(Integer id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}