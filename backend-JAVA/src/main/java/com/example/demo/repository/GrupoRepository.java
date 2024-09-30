package com.example.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Grupo;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, String> {
    // Métodos de consulta personalizados
}
